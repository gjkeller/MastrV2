/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands.fun;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.command.CommandEvent;

public class BubbleWrapCommand extends CommandBase {
    private static final String BUBBLES = "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| \n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| \n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| \n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| \n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||\n" +
            "||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP|| ||POP||";

    public BubbleWrapCommand(Mastr mastr) {
        super(mastr);
        this.command = "bubblewrap";
        this.description = "Sends bubble wrap for your pleasure";
        this.category = CommandCategory.FUN;
        this.syntax = new String[] {""};
        this.aliases = new String[] {"bubbles"};
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    // This is a dumb command.
    // I'm sorry.
    @Override
    public void execute(CommandEvent e) {
        e.getChannel().sendMessage(BUBBLES).queue();
    }
}
