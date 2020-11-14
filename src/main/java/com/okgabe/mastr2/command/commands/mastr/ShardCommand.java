/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands.mastr;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.command.CommandEvent;

public class ShardCommand extends CommandBase {
    public ShardCommand(Mastr mastr) {
        super(mastr);
        this.command = "shard";
        this.description = "Get what shard the bot is currently running on";
        this.category = CommandCategory.MASTR;
        this.syntax = new String[] {""};
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    @Override
    public void execute(CommandEvent e) {
        e.getChannel().sendMessage("You are on shard #" + e.getJDA().getShardInfo().getShardId() + " out of " + e.getJDA().getShardInfo().getShardTotal()).queue();
    }
}
