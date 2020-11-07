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
import com.okgabe.mastr2.command.CommandEvent;
import com.okgabe.mastr2.permission.BotRole;
import com.okgabe.mastr2.util.GuildUtil;
import com.okgabe.mastr2.util.StringUtil;

public class SearchForUserCommand extends CommandBase {
    public SearchForUserCommand(Mastr mastr) {
        super(mastr);
        this.command = "searchforuser";
        this.category = CommandCategory.MASTR_ADMIN;
        this.description = "Uses the bot's built-in searching function to find a specified user.";
        this.minimumRole = BotRole.BOT_TESTER;
        this.syntax = new String[] {"<user>"};
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length > 0;
    }

    @Override
    public void execute(CommandEvent e) {
        if(!e.isInGuild()) return;

        String search = e.getArgs()[0];
        GuildUtil.retrieveMemberByName(mastr, e.getMessage().getTextChannel(), e.getAuthor(), search, true)
                .onError(err -> e.getChannel().sendMessage(err.getMessage()).queue())
                .onSuccess(member -> e.getChannel().sendMessage("Your search returned " + StringUtil.nameAndTag(member) + " (" + member.getId() + ")").queue());
    }
}
