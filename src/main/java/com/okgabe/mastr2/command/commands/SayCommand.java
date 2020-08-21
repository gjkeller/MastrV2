/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.StringUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class SayCommand extends CommandBase {

    public SayCommand(Mastr mastr) {
        super(mastr);
    }

    @Override
    public boolean called(String[] args) {
        return args.length > 0;
    }

    @Override
    public void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args) {
        message.delete().queue();
        channel.sendMessage(StringUtil.join(args)).queue();
    }

    @Override
    public BotRole getMinimumRole() {
        return BotRole.BOT_STAFF;
    }

    @Override
    public String getCommand() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Forces the bot to say something";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MASTR;
    }

    @Override
    public String[] getSyntax() {
        return new String[] {"<message>"};
    }
}
