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

public class HelpCommand extends CommandBase {
    public HelpCommand(Mastr mastr) {
        super(mastr);
        this.command = "help";
        this.aliases = new String[] {"h", "command", "cmd"};
        this.description = "Describes how to use Mastr and its commands";
        this.category = CommandCategory.MASTR;
        this.syntax = new String[] {"[page]", "[command]"};
        this.examples = new String[] {"help 4", "help kick"};
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    @Override
    public void execute(CommandEvent e) {

    }
}
