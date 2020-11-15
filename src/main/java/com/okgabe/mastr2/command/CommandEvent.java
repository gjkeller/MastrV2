/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.util.ColorConstants;
import com.okgabe.mastr2.util.EmoteConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.function.Consumer;

public class CommandEvent {

    private Mastr mastr;
    private Member author;
    private BotGuild botGuild;
    private BotUser botUser;
    private MessageChannel channel;
    private Message message;
    private String[] args;

    public CommandEvent(Mastr mastr, Member author, BotGuild botGuild, BotUser botUser, MessageChannel channel, Message message, String[] args) {
        this.mastr = mastr;
        this.author = author;
        this.botGuild = botGuild;
        this.botUser = botUser;
        this.channel = channel;
        this.message = message;
        this.args = args;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public BotGuild getBotGuild() {
        return botGuild;
    }

    public void setBotGuild(BotGuild botGuild) {
        this.botGuild = botGuild;
    }

    public BotUser getBotUser() {
        return botUser;
    }

    public void setBotUser(BotUser botUser) {
        this.botUser = botUser;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Guild getGuild(){
        return message.getGuild();
    }

    public MessageAction reply(String message){
        return channel.sendMessage(message);
    }

    public MessageAction reply(MessageEmbed message){
        return channel.sendMessage(message);
    }

    public void reply(String message, Consumer<Message> afterExecute){
        channel.sendMessage(message).queue(afterExecute);
    }

    public void reply(MessageEmbed message, Consumer<Message> afterExecute){
        channel.sendMessage(message).queue(afterExecute);
    }

    public MessageAction replyError(String message){
        return replyError(message, null);
    }

    public MessageAction replyError(String title, String message){
        return channel.sendMessage(new EmbedBuilder()
            .setTitle(EmoteConstants.X_SYMBOL + " " + title)
            .setDescription(message)
            .setColor(ColorConstants.ERROR_COLOR)
            .build()
        );
    }

    public MessageAction replySuccess(String message){
        return replySuccess(message, null);
    }

    public MessageAction replySuccess(String title, String message){
        return channel.sendMessage(new EmbedBuilder()
                .setTitle(EmoteConstants.CHECK_SYMBOL + " " + title)
                .setDescription(message)
                .setColor(ColorConstants.SUCCESS_COLOR)
                .build()
        );
    }

    public boolean isInGuild(){
        return message.isFromType(ChannelType.TEXT);
    }

    public JDA getJDA(){
        return author.getJDA();
    }

    public Mastr getMastr() {
        return mastr;
    }
}
