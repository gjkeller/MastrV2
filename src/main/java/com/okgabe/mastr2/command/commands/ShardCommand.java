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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ShardCommand extends CommandBase {
    public ShardCommand(Mastr mastr) {
        super(mastr);
    }

    @Override
    public boolean called(String[] args) {
        return true;
    }

    @Override
    public void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args) {
        channel.sendMessage("You are on shard #" + author.getJDA().getShardInfo().getShardId() + " out of " + author.getJDA().getShardInfo().getShardTotal()).queue();
    }

    @Override
    public String getCommand() {
        return "shard";
    }

    @Override
    public String getDescription() {
        return "Get what shard the bot is currently running on";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MASTR;
    }

    @Override
    public String[] getSyntax() {
        return new String[] {""};
    }
}
