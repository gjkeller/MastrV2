/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastrv2.util;

public enum LogType {
    WASTE(5), DEBUG(4), INFO(3), WARN(2), CRITICAL(1);

    private int level;

    LogType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
