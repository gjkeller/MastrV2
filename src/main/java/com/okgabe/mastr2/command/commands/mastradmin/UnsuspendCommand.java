/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands.mastradmin;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.command.CommandEvent;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.permission.BotRole;
import com.okgabe.mastr2.util.Checks;

public class UnsuspendCommand extends CommandBase {
    public UnsuspendCommand(Mastr mastr) {
        super(mastr);
        this.command = "unsuspend";
        this.description = "Unsuspends the given user";
        this.syntax = new String[] {"<userid>"};
        this.minimumRole = BotRole.BOT_ADMINISTRATOR;
        this.category = CommandCategory.MASTR_ADMIN;
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length == 1;
    }

    @Override
    public void execute(CommandEvent e) {
        if(!Checks.isId(e.getArgs()[0])){
            e.replyError("Please provide a valid user ID to suspend").queue();
            return;
        }

        long id = Long.parseLong(e.getArgs()[0]);
        BotUser target = mastr.getDatabaseManager().getBotUser(id);
        if(!e.getBotUser().getRole().isAtOrAbove(target.getRole())){
            e.replyError("You don't have permission to interact with this individual").queue();
            return;
        }

        boolean result = mastr.getPermissionManager().unsuspend(target);
        if(result){
            e.replySuccess(" That individual has been unsuspended").queue();
        }
        else{
            e.replyError("That individual was not suspended").queue();
        }
    }
}
