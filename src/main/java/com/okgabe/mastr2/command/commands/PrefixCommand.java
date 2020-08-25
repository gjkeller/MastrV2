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
import com.okgabe.mastr2.util.StringUtil;

public class PrefixCommand extends CommandBase {

    private static final char[] WHITELISTED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm,./<>?1234567890!@#$%^&*()-=_+[{;:'\"".toCharArray();

    public PrefixCommand(Mastr mastr) {
        super(mastr);
        this.command = "prefix";
        this.description = "Change the bot's prefix for this server";
        this.syntax = new String[] {"<prefix>"};
        this.category = CommandCategory.MASTR;
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    @Override
    public void execute(CommandEvent e) {
        if(e.getArgs().length == 0){
            e.getChannel().sendMessage("This server's prefix is `" + e.getBotGuild().getPrefix() + "`").queue();
            return;
        }
        else if(e.getArgs().length>2){
            e.getChannel().sendMessage("❌ Your prefix must not have more than one space").queue();
            return;
        }

        String prefix = StringUtil.join(e.getArgs());

        if(e.getBotGuild().getPrefix().equals(prefix)){
            e.getChannel().sendMessage("❌ This server's prefix is already `" + prefix + "`").queue();
            return;
        }

        if(prefix.length()>20){
            e.getChannel().sendMessage("❌ Your prefix cannot be longer than 20 characters").queue();
            return;
        }

        for(char prefixChar : prefix.toCharArray()){
            boolean isWhitelistedChar = false;
            for(char allowedChar : prefix.toCharArray()){
                if(prefixChar == allowedChar) {
                    isWhitelistedChar = true;
                    break;
                }
            }

            if(!isWhitelistedChar){
                e.getChannel().sendMessage("❌ That message contains a blacklisted character: " + prefixChar).queue();
                return;
            }
        }

        e.getBotGuild().setPrefix(prefix);
        e.getBotGuild().set(mastr.getDatabaseManager());
        mastr.getCacheManager().setPrefix(e.getBotGuild().getGuildId(), prefix);
        e.getChannel().sendMessage("✅ This server's prefix has been changed to `" + prefix + "`").queue();
    }
}
