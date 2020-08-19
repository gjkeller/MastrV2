/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

public enum BotRole {
    DEFAULT("Default", 0), BOT_STAFF("Bot Staff", 1), BOT_ADMINISTRATOR("Bot Administrator", 2), BOT_MANAGER("Bot Manager", 3);

    private String name;
    private int level;

    BotRole(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public static BotRole parse(String name){
        switch(name.toLowerCase()){
            case "default":
                return DEFAULT;
            case "bot staff":
                return BOT_STAFF;
            case "bot administrator":
                return BOT_ADMINISTRATOR;
            case "bot manager":
                return BOT_MANAGER;
            default:
                return null;
        }
    }
}
