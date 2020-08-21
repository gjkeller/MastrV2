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
import com.okgabe.mastr2.util.BotRole;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandBase {

    protected Mastr mastr;
    protected Logger logger;

    public CommandBase(Mastr mastr){
        this.mastr = mastr;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public abstract boolean called(String[] args);

    public abstract void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args);

    public abstract String getCommand();

    public abstract String getDescription();

    public abstract CommandCategory getCategory();

    public abstract String[] getSyntax();

    public String[] getAliases(){
        return new String[] {};
    }

    public BotRole getMinimumRole() {
        return BotRole.DEFAULT;
    }

    public boolean isShownInHelp() {
        return true;
    }

    public String[] getExamples() { return new String[] {}; }
}
