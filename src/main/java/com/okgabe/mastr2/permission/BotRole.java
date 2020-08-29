/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.permission;

public enum BotRole {
    UNKNOWN("Unknown", -1), DEFAULT("Default", 5), BOT_STAFF("Bot Staff", 20),
    BOT_ADMINISTRATOR("Bot Administrator", 50), BOT_MANAGER("Bot Manager", 100), BOT_FOUNDER("Bot Founder", 127);

    private final String name;
    private final int level;

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
        String n = name.toLowerCase();
        for(BotRole r : values()){
            if(r.getName().toLowerCase().equals(n)) return r;
        }

        return UNKNOWN;
    }

    public static BotRole parse(int level){
        for(BotRole r : values()){
            if(r.getLevel() == level) return r;
        }
        
        return UNKNOWN;
    }

    @Override
    public String toString(){
        return name;
    }
}
