/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.util.BotRole;

public abstract class CommandBase {

    private Mastr mastr;
    private String command;
    private String description;
    private String[] aliases;
    private String[] syntax;
    private BotRole minimumRole;
    private boolean shownInHelp = true;
    private CommandCategory category;

    public CommandBase(Mastr mastr){
        this.mastr = mastr;
    }

//    public abstract boolean called

    public abstract String getCommand();

    public abstract String getDescription();

    public abstract CommandCategory getCategory();

    public abstract String[] getSyntax();

    public String[] getAliases(){
        return new String[] {};
    }


    public BotRole getMinimumRole() {
        return BotRole.DEFAULT;
    }

    public boolean isShownInHelp() {
        return shownInHelp;
    }
}
