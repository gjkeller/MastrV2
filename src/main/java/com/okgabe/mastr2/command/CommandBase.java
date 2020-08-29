/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.permission.BotRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandBase {

    protected Mastr mastr;
    protected Logger logger;

    // Required
    protected String command;
    protected String description;
    protected CommandCategory category;
    protected String[] syntax;

    // Optional
    protected String[] aliases = new String[] {};
    protected String[] examples = new String[] {};
    protected BotRole minimumRole = BotRole.DEFAULT;
    protected boolean shownInHelp = true;

    public CommandBase(Mastr mastr){
        this.mastr = mastr;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public abstract boolean called(CommandEvent e);

    public abstract void execute(CommandEvent e);

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public CommandCategory getCategory() {
        return category;
    }

    public String[] getSyntax() {
        return syntax;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String[] getExamples() {
        return examples;
    }

    public BotRole getMinimumRole() {
        return minimumRole;
    }

    public boolean isShownInHelp() {
        return shownInHelp;
    }
}
