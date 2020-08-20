/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

public enum BotRole {
    UNKNOWN("Unknown", -1), PERMANENT_SUSPENSION("Permanent Suspension", 0), TEMPORARY_SUSPENSION("Temporary Suspension", 1),
    AUTOMATICALLY_SUSPENDED("Automatic Suspension", 2), DEFAULT("Default", 5), BOT_STAFF("Bot Staff", 20),
    BOT_ADMINISTRATOR("Bot Administrator", 50), BOT_MANAGER("Bot Manager", 100), BOT_FOUNDER("Bot Founder", 127);

    private String name;
    private int level;

    BotRole(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public boolean isAtOrAbove(BotRole role){
        return this.level >= role.getLevel();
    }

    public boolean isAbove(BotRole role){
        return this.level > role.getLevel();
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
                return UNKNOWN;
        }
    }

    public static BotRole parse(int level){
        for(BotRole r : values()){
            if(r.level == level) return r;
        }
        
        return null;
    }

    @Override
    public String toString(){
        return name;
    }
}
