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
import com.okgabe.mastr2.util.StringUtil;

public class SayCommand extends CommandBase {

    public SayCommand(Mastr mastr) {
        super(mastr);
        this.command = "say";
        this.description = "Forces the bot to say something";
        this.category = CommandCategory.MASTR_ADMIN;
        this.syntax = new String[] {"<message>"};
        this.minimumRole = BotRole.BOT_ADMINISTRATOR;
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length > 0;
    }

    @Override
    public void execute(CommandEvent e) {
        e.getMessage().delete().queue();
        e.getChannel().sendMessage(StringUtil.join(e.getArgs())).queue();
    }
}
