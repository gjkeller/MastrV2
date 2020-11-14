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
import com.okgabe.mastr2.util.StringUtil;

public class SetBotRoleCommand extends CommandBase {
    public SetBotRoleCommand(Mastr mastr) {
        super(mastr);
        this.command = "setbotrole";
        this.syntax = new String[] {"<userid> <role>"};
        this.description = "Changes the user's role within the bot.";
        this.minimumRole = BotRole.BOT_MANAGER;
        this.category = CommandCategory.MASTR_ADMIN;
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length > 1;
    }

    @Override
    public void execute(CommandEvent e) {
        if(!Checks.isId(e.getArgs()[0])){
            e.replyError("Please provide a valid user ID to set the role of").queue();
            return;
        }

        long id = Long.parseLong(e.getArgs()[0]);
        BotUser targetUser = mastr.getDatabaseManager().getBotUser(id, false);
        if(targetUser.getRole() == BotRole.UNKNOWN){
            e.replyError("The specified user does not exist").queue();
            return;
        }

        if(!e.getBotUser().getRole().isAtOrAbove(targetUser.getRole())){
            e.replyError("You don't have permission to interact with this individual").queue();
            return;
        }

        String search = StringUtil.join(e.getArgs(), 1);
        BotRole targetRole = BotRole.find(search);
        if(targetRole == null){
            e.replyError("I couldn't find the role `" + search + "`",
                    "Available roles include: " + StringUtil.join(BotRole.values(), ", ")).queue();
            return;
        }

        if(targetRole.isAtOrAbove(e.getBotUser().getRole())){
            e.replyError("You don't have permission to interact with this role").queue();
            return;
        }

        targetUser.setRole(targetRole);
        targetUser.set(mastr.getDatabaseManager());

        e.getJDA().retrieveUserById(targetUser.getUserId()).queue(u -> {
            e.replySuccess((u == null ? targetUser.getUserId() : u.getName()) + "'s role has been changed to " + targetRole.getName()).queue();
        });
    }
}
