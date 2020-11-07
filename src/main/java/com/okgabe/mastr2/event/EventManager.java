/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.event;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventManager extends ListenerAdapter {

    private Mastr mastr;

    public EventManager(Mastr mastr) {
        this.mastr = mastr;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        // Ignore bots and Mastr itself
        if(e.getAuthor().isBot()) return;
        if(e.isWebhookMessage()) return;

        if(e.isFromType(ChannelType.PRIVATE)){
            // Check if user is suspended, if so, return
            BotUser user = mastr.getDatabaseManager().getBotUser(e.getAuthor().getIdLong());
            if(mastr.getPermissionManager().isBannedUser(user)) return;

            // Pass on the user's message to DM Handler
            mastr.getDirectMessageHandler().handleMessage(e, user);
        }
        else{
            // Check if guild is suspended, if so, return
            BotGuild guild = mastr.getDatabaseManager().getBotGuild(e.getGuild().getIdLong());
            if(mastr.getPermissionManager().isBannedGuild(guild)) return;
            // TODO: Add message caching here

            // Check if user is suspended, if so, return
            BotUser user = mastr.getDatabaseManager().getBotUser(e.getAuthor().getIdLong());
            if(mastr.getPermissionManager().isBannedUser(user)) return;

            // Check if ResponseHandler is listening for this user's message, if so, return
            if(mastr.getResponseHandler().handleMessage(e.getMessage())) return;

            // Check if user issued a command
            mastr.getCommandHandler().handleMessage(e, user, guild);
        }
    }

    public void onUserTyping(UserTypingEvent e){

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e){
        mastr.getReactionHandler().handleReaction(e.getReaction(), e.getMessageIdLong(), e.getChannelType(), e.getUser());
    }
}