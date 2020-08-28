/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.event;

import com.okgabe.mastr2.Mastr;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReactionHandler {

    private Mastr mastr;
    private List<ReactionListenerIdentity> guildReactionListeners;
    private List<ReactionListenerIdentity> dmReactionListeners;

    public ReactionHandler(Mastr mastr) {
        this.mastr = mastr;
        this.guildReactionListeners = new ArrayList<>();
        this.dmReactionListeners = new ArrayList<>();
    }

    public void handleReaction(MessageReaction reaction, long messageId, ChannelType channelType, User user){
        if(channelType == ChannelType.TEXT){
            for(ReactionListenerIdentity identity : guildReactionListeners){
                if(identity.getMessageId() == messageId && user.getIdLong() == identity.getUserId()){
                    identity.setReaction(reaction);
                    identity.getHandler().accept(identity);
                }
            }
        }
        else if(channelType == ChannelType.PRIVATE){
            for(ReactionListenerIdentity identity : dmReactionListeners){
                if(identity.getMessageId() == messageId && user.getIdLong() == identity.getUserId()){
                    identity.setReaction(reaction);
                    identity.getHandler().accept(identity);
                }
            }
        }
    }

    public void register(ReactionListenerIdentity reactionIdentity){
        if(reactionIdentity.getChannelType() == ChannelType.PRIVATE){
            dmReactionListeners.add(reactionIdentity);
        }
        else if(reactionIdentity.getChannelType() == ChannelType.TEXT){
            guildReactionListeners.add(reactionIdentity);
        }

        ScheduledFuture<?> timeoutSchedule = mastr.getScheduler().schedule(() -> {
            reactionIdentity.getTimeoutHandler().accept(reactionIdentity);
            if(reactionIdentity.getChannelType()==ChannelType.TEXT)
                guildReactionListeners.remove(reactionIdentity);
            else if(reactionIdentity.getChannelType()==ChannelType.PRIVATE)
                dmReactionListeners.remove(reactionIdentity);
        }, reactionIdentity.getTimeout(), TimeUnit.SECONDS);

        reactionIdentity.setTimeoutSchedule(timeoutSchedule);
    }

    public void unregister(ReactionListenerIdentity reactionIdentity){
        reactionIdentity.getTimeoutSchedule().cancel(false);
        if(reactionIdentity.getChannelType()==ChannelType.TEXT)
            guildReactionListeners.remove(reactionIdentity);
        else if(reactionIdentity.getChannelType()==ChannelType.PRIVATE)
            dmReactionListeners.remove(reactionIdentity);
    }
}
