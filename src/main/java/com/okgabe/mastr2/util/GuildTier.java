/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

public enum GuildTier {

    UNKNOWN("Unknown", -1), SUSPENDED_GUILD("Suspended Guild", 0), TEMPORARILY_SUSPENDED_GUILD("Temporarily Suspended Guild", 1),
    AUTOMATICALLY_SUSPENDED_GUILD("Automatically Suspended Guild", 2), DEFAULT("Default Guild", 5), PREMIUM_GUILD("Premium Guild", 20),
    VIP_GUILD("VIP Guild", 50), OFFICIAL_GUILD("Official Guild", 100);

    private int level;
    private String name;

    GuildTier(String name, int level) {
        this.level = level;
        this.name = name;
    }

    public boolean isAtOrAbove(GuildTier tier){
        return this.level >= tier.getLevel();
    }

    public boolean isAbove(GuildTier tier){
        return this.level > tier.getLevel();
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public static GuildTier parse(String name){
        String n = name.toLowerCase();
        for(GuildTier g : values()){
            if(g.getName().toLowerCase().equals(n)) return g;
        }

        return UNKNOWN;
    }

    public static GuildTier parse(int level){
        for(GuildTier g : values()){
            if(g.getLevel() == level) return g;
        }

        return UNKNOWN;
    }

    @Override
    public String toString(){
        return name;
    }
}
