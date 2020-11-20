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

public class PingCommand extends CommandBase {

    public PingCommand(Mastr mastr) {
        super(mastr);

        this.command = "ping";
        this.description = "Gets the ping of the bot's connection";
        this.category = CommandCategory.MASTR;
        this.syntax = new String[] {""};
        this.aliases = new String[] {"hey", "hi", "hello"};
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    @Override
    public void execute(CommandEvent e) {
        long before = e.getMessage().getTimeCreated().toInstant().toEpochMilli();
        e.getChannel().sendMessage("Ping! \uD83D\uDC93 `" + e.getJDA().getGatewayPing() + "`ms").queue(m -> {
            m.editMessage( m.getContentRaw() + "  /  ↔️ `" + (m.getTimeCreated().toInstant().toEpochMilli() - before) + "`ms").queue();
        });
    }
}
