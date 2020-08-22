/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

import com.okgabe.mastr2.Mastr;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Listens for responses in DMs and text channels
 * Useful for games without utilizing the bot's prefix, support systems in DMs, interactive commands, et. cetera.
 */
public class ResponseHandler {

    private Mastr mastr;
    private List<ResponseListenerIdentity> guildResponseListeners;
    private List<ResponseListenerIdentity> dmResponseListeners;

    public ResponseHandler(Mastr mastr) {
        this.mastr = mastr;
        guildResponseListeners = new ArrayList<>();
        dmResponseListeners = new ArrayList<>();
    }

    public boolean handleMessage(Message m){
        if(m.isFromType(ChannelType.TEXT)){
            for(ResponseListenerIdentity identity : guildResponseListeners){
                if(identity.getUserId() == m.getAuthor().getIdLong() && identity.getChannelId() == m.getChannel().getIdLong()){
                    identity.setMessage(m);
                    identity.getHandler().accept(identity);
                    return true;
                }
            }
        }
        else if(m.isFromType(ChannelType.PRIVATE)){
            for(ResponseListenerIdentity identity : dmResponseListeners){
                if(identity.getUserId() == m.getAuthor().getIdLong() && identity.getChannelId() == m.getChannel().getIdLong()){
                    identity.setMessage(m);
                    identity.getHandler().accept(identity);
                    return true;
                }
            }
        }

        return false;
    }

    public void register(ResponseListenerIdentity responseIdentity){
        if(responseIdentity.getChannelType()==ChannelType.TEXT)
            guildResponseListeners.add(responseIdentity);
        else if(responseIdentity.getChannelType()==ChannelType.PRIVATE)
            dmResponseListeners.add(responseIdentity);

        ScheduledFuture<?> timeoutSchedule = mastr.getScheduler().schedule(() -> {
            responseIdentity.getTimeoutHandler().accept(responseIdentity);
            if(responseIdentity.getChannelType()==ChannelType.TEXT)
                guildResponseListeners.remove(responseIdentity);
            else if(responseIdentity.getChannelType()==ChannelType.PRIVATE)
                dmResponseListeners.remove(responseIdentity);
        }, responseIdentity.getTimeout(), TimeUnit.SECONDS);

        responseIdentity.setTimeoutSchedule(timeoutSchedule);
    }

    public void unregister(ResponseListenerIdentity identity){
        identity.getTimeoutSchedule().cancel(false);
        if(identity.getChannelType()==ChannelType.TEXT)
            guildResponseListeners.remove(identity);
        else if(identity.getChannelType()==ChannelType.PRIVATE)
            dmResponseListeners.remove(identity);
    }
}
