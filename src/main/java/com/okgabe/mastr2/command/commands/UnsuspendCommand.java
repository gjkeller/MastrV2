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
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.Checks;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class UnsuspendCommand extends CommandBase {
    public UnsuspendCommand(Mastr mastr) {
        super(mastr);
    }

    @Override
    public boolean called(String[] args) {
        return args.length == 1;
    }

    @Override
    public void execute(Member author, BotUser user, MessageChannel channel, Message message, String[] args) {
        if(!Checks.isId(args[0])){
            channel.sendMessage("❌ Please provide a valid user ID to suspend").queue();
            return;
        }

        long id = Long.parseLong(args[0]);
        BotUser target = mastr.getDatabaseManager().getBotUser(id);
        if(!user.getRole().isAtOrAbove(target.getRole())){
            channel.sendMessage("❌ You don't have permission to interact with this individual").queue();
            return;
        }

        boolean result = mastr.getPermissionManager().unsuspend(target);
        if(result){
            channel.sendMessage("✅ That individual has been unsuspended").queue();
        }
        else{
            channel.sendMessage("❌ That individual was not suspended").queue();
        }
    }

    @Override
    public BotRole getMinimumRole() {
        return BotRole.BOT_ADMINISTRATOR;
    }

    @Override
    public String getCommand() {
        return "unsuspend";
    }

    @Override
    public String getDescription() {
        return "Unsuspends the given user";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MASTR_ADMIN;
    }

    @Override
    public String[] getSyntax() {
        return new String[] {"<userid>"};
    }
}
