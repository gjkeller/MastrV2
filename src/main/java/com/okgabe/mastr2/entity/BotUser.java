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
    private long suspensionEnd;

    public BotUser(long userId, BotRole role, int timesUsed, long suspensionEnd){
        this.userId = userId;
        this.role = role;
        this.timesUsed = timesUsed;
        this.suspensionEnd = suspensionEnd;
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

    public long getSuspensionEnd() {
        return suspensionEnd;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setRole(BotRole role) {
        this.role = role;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public void setSuspensionEnd(long suspensionEnd) {
        this.suspensionEnd = suspensionEnd;
    }

    @Override
    public String toString() {
        return "BotUser{" +
                "userId=" + userId +
                ", role=" + role +
                ", timesUsed=" + timesUsed +
                ", suspensionEnd=" + suspensionEnd +
                '}';
    }
}
