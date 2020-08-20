/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.entity;

import com.okgabe.mastr2.util.BotRole;

public class BotUser {
    private long userId;
    private BotRole role;
    private int timesUsed;
    private long suspensionLength;

    public BotUser(long userId, BotRole role, int timesUsed, long suspensionLength){
        this.userId = userId;
        this.role = role;
        this.timesUsed = timesUsed;
        this.suspensionLength = suspensionLength;
    }

    public long getUserId() {
        return userId;
    }

    public BotRole getRole() {
        return role;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public long getSuspensionLength() {
        return suspensionLength;
    }
}
