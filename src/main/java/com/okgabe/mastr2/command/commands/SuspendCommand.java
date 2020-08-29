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
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.permission.BotRole;
import com.okgabe.mastr2.util.Checks;
import com.okgabe.mastr2.util.SuspensionCode;
import com.okgabe.mastr2.util.TimeUtil;

public class SuspendCommand extends CommandBase {

    public SuspendCommand(Mastr mastr) {
        super(mastr);
        this.command = "suspend";
        this.description = "Suspends an individual from using the Mastr";
        this.category = CommandCategory.MASTR_ADMIN;
        this.syntax = new String[] {"<userid> - Permanent suspension", "<userid> <time> - Temporary suspension"};
        this.minimumRole = BotRole.BOT_ADMINISTRATOR;
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length > 0 && e.getArgs().length < 3;
        // suspend <userid> <time or leave blank for permanent>
    }

    @Override
    public void execute(CommandEvent e) {

        if(!Checks.isId(e.getArgs()[0])){
            e.getChannel().sendMessage("❌ Please provide a valid user ID to suspend").queue();
            return;
        }

        long id = Long.parseLong(e.getArgs()[0]);
        BotUser target = mastr.getDatabaseManager().getBotUser(id);
        if(!e.getBotUser().getRole().isAtOrAbove(target.getRole())){
            e.getChannel().sendMessage("❌ You don't have permission to interact with this individual").queue();
            return;
        }

        if(e.getArgs().length == 1){
            boolean result = mastr.getPermissionManager().suspend(target);
            if(result){
                e.getChannel().sendMessage("✅ That individual has been permanently suspended from use of Mastr").queue();
            }
            else{
                e.getChannel().sendMessage("❌ That individual is already suspended").queue();
            }
        }
        else{
            String time = e.getArgs()[1];
            long timeSeconds = TimeUtil.toSeconds(time);

            boolean result = mastr.getPermissionManager().suspend(target, SuspensionCode.TEMPORARY_SUSPENSION, timeSeconds + TimeUtil.getNow());
            if(result){
                e.getChannel().sendMessage("✅ That individual has been temporarily suspended for " + TimeUtil.toStringLong(timeSeconds)).queue();
            }
            else{
                e.getChannel().sendMessage("❌ That individual is already permanently suspended").queue();
            }
        }
    }
}
