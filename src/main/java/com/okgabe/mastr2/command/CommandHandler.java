/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.entity.BotUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {

    private Mastr mastr;
    private String mastrId;

    public CommandHandler(Mastr mastr) {
        this.mastr = mastr;
        mastrId = mastr.getShardManager().getShardById(0).getSelfUser().getId();
    }

    public void handleMessage(MessageReceivedEvent e){
        String content = e.getMessage().getContentRaw();
        String prefix = retrievePrefix(e.getGuild().getIdLong());
        boolean commandTriggered = false;
        boolean mentioned = false;

        if(content.startsWith(prefix)) {
            commandTriggered = true;
        }
        else if(content.startsWith("<@" + mastrId + ">") || content.startsWith("<@!"  + mastrId + ">")) {
            commandTriggered = true;
            mentioned = true;
        }

        if(commandTriggered){
            // Check if user is banned
            BotUser user = mastr.getDatabaseManager().getBotUser(e.getAuthor().getIdLong());
            if(mastr.getPermissionManager().isBannedUser(user)) return;

            // Check to see if the user is only pinging the bot
            if(content.trim().length() <= 22 && mentioned){
                e.getChannel().sendMessage("Hey! My prefix for this server is `" + prefix + "`. If you need help, you can use `" + prefix + "help` or DM me!" +
                        "\nIf you don't like remembering bot prefixes, mentioning me works as a command prefix as well.").queue();
                return;
            }

            String[] dividedMessage = content.split(" ");
//            String command = dividedMessage
        }
    }

    private String retrievePrefix(long guildId){
        String cached = mastr.getCacheManager().getPrefix(guildId);

        if(cached!=null) return cached;

        String prefix = mastr.getDatabaseManager().getGuildPrefix(guildId);
        mastr.getCacheManager().setPrefix(guildId, prefix);
        return prefix;
    }
}
