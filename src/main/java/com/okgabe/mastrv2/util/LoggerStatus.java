/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastrv2.util;

public enum LoggerStatus {
    WASTE(5), DEBUG(4), INFO(3), WARN(2), NOTHING(0);

    private int level;

    LoggerStatus(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static LoggerStatus parseStatus(String name){
        switch(name.toLowerCase()){
            case "waste":
                return WASTE;
            case "debug":
                return DEBUG;
            case "info":
                return INFO;
            case "warn":
                return WARN;
            default:
                return null;
        }
    }
}
