/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.event;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

public class ResponseListener {

    private ChannelType channelType;
    private long channelId;
    private long userId;
    private long timeout;
    private Consumer<ResponseListener> handler;
    private Consumer<ResponseListener> timeoutHandler;
    private ScheduledFuture<?> timeoutSchedule;
    private Message message;

    public ResponseListener(ChannelType channelType, long channelId, long userId, long timeout, Consumer<ResponseListener> handler, Consumer<ResponseListener> timeoutHandler) {
        if(channelType!=ChannelType.PRIVATE && channelType!=ChannelType.TEXT){
            throw new IllegalArgumentException("Provided an invalid ChannelType");
        }
        this.channelType = channelType;
        this.channelId = channelId;
        this.userId = userId;
        this.timeout = timeout;
        this.handler = handler;
        this.timeoutHandler = timeoutHandler;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public long getChannelId() {
        return channelId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTimeout() {
        return timeout;
    }

    public Consumer<ResponseListener> getHandler() {
        return handler;
    }

    public Consumer<ResponseListener> getTimeoutHandler() {
        return timeoutHandler;
    }

    public ScheduledFuture<?> getTimeoutSchedule() {
        return timeoutSchedule;
    }

    public void setTimeoutSchedule(ScheduledFuture<?> timeoutSchedule) {
        this.timeoutSchedule = timeoutSchedule;
    }
}
