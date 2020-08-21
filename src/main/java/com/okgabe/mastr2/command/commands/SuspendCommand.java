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
import com.okgabe.mastr2.util.Checks;
import com.okgabe.mastr2.util.SuspensionCode;
import com.okgabe.mastr2.util.TimeUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class SuspendCommand extends CommandBase {

    public SuspendCommand(Mastr mastr) {
        super(mastr);
    }

    @Override
    public boolean called(String[] args) {
        return args.length > 0 && args.length < 3;
        // suspend <userid> <time or leave blank for permanent>
    }

    @Override
    public void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args) {

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

        if(args.length == 1){
            boolean result = mastr.getPermissionManager().suspend(target);
            if(result){
                channel.sendMessage("✅ That individual has been permanently suspended from use of Mastr").queue();
            }
            else{
                channel.sendMessage("❌ That individual is already suspended").queue();
            }
        }
        else{
            String time = args[1];
            long timeSeconds = TimeUtil.toSeconds(time);

            boolean result = mastr.getPermissionManager().suspend(target, SuspensionCode.TEMPORARY_SUSPENSION, timeSeconds + TimeUtil.getNow());
            if(result){
                channel.sendMessage("✅ That individual has been temporarily suspended for " + TimeUtil.toStringLong(timeSeconds)).queue();
            }
            else{
                channel.sendMessage("❌ That individual is already permanently suspended").queue();
            }
        }
    }

    @Override
    public String getCommand() {
        return "suspend";
    }

    @Override
    public String getDescription() {
        return "Suspends an individual from using the Mastr";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MASTR_ADMIN;
    }

    @Override
    public String[] getSyntax() {
        return new String[] {"<userid> - Permanent suspension", "<userid> <time> - Temporary Suspension"};
    }

    @Override
    public BotRole getMinimumRole() {
        return BotRole.BOT_ADMINISTRATOR;
    }
}
