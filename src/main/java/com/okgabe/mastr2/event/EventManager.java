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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventManager extends ListenerAdapter {

    private Mastr bot;

    public EventManager(Mastr bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        // Ignore bots and Mastr itself
        if(e.getAuthor().isBot()) return;
        if(e.isWebhookMessage()) return;

        if(e.isFromType(ChannelType.PRIVATE)){

        }
        else{

        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e){
        // Ignore bots and Mastr itself
        if(e.getAuthor().isBot()) return;

        if(e.isFromType(ChannelType.PRIVATE)){

        }
        else{

        }
    }
}