/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.event;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

public class ReactionListenerIdentity {

    private JDA jda;
    private ChannelType channelType;
    private long messageId;
    private long userId;
    private long timeout;
    private long channelId;
    private Consumer<ReactionListenerIdentity> handler;
    private Consumer<ReactionListenerIdentity> timeoutHandler;

    private ScheduledFuture<?> timeoutSchedule;
    private MessageReaction reaction;

    public ReactionListenerIdentity(JDA jda, ChannelType channelType, long channelId, long messageId, long userId, long timeout, Consumer<ReactionListenerIdentity> handler, Consumer<ReactionListenerIdentity> timeoutHandler) {
        this.jda = jda;
        this.channelType = channelType;
        this.channelId = channelId;
        this.messageId = messageId;
        this.userId = userId;
        this.timeout = timeout;
        this.handler = handler;
        this.timeoutHandler = timeoutHandler;
    }

    public void setTimeoutSchedule(ScheduledFuture<?> timeoutSchedule) {
        this.timeoutSchedule = timeoutSchedule;
    }

    public void setReaction(MessageReaction reaction) {
        this.reaction = reaction;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTimeout() {
        return timeout;
    }

    public Consumer<ReactionListenerIdentity> getHandler() {
        return handler;
    }

    public Consumer<ReactionListenerIdentity> getTimeoutHandler() {
        return timeoutHandler;
    }

    public ScheduledFuture<?> getTimeoutSchedule() {
        return timeoutSchedule;
    }

    public MessageReaction getReaction() {
        return reaction;
    }

    public JDA getJda() {
        return jda;
    }

    public long getChannelId() {
        return channelId;
    }

    public RestAction<Message> retrieveMessage(){
        MessageChannel chnl;
        if(channelType==ChannelType.PRIVATE){
            chnl = jda.getPrivateChannelById(channelId);
        }
        else{
            chnl = (MessageChannel)jda.getGuildChannelById(channelId);
        }

        if(chnl != null){
            return chnl.retrieveMessageById(messageId);
        }
        else{
            return null;
        }
    }

    public MessageChannel getChannel(){
        if(channelType==ChannelType.PRIVATE){
            return jda.getPrivateChannelById(channelId);
        }
        else{
            return (MessageChannel)jda.getGuildChannelById(channelId);
        }
    }

    public User getUser(){
        return jda.getUserById(userId);
    }
}
