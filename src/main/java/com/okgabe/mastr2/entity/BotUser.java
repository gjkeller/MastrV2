/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.entity;

import com.okgabe.mastr2.db.DatabaseManager;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.SuspensionCode;

public class BotUser {
    private final long userId;
    private BotRole role;
    private int timesUsed;
    private SuspensionCode suspensionCode;
    private long suspensionEnd;

    public BotUser(long userId, BotRole role, int timesUsed, SuspensionCode suspensionCode, long suspensionEnd){
        this.userId = userId;
        this.role = role;
        this.timesUsed = timesUsed;
        this.suspensionCode = suspensionCode;
        this.suspensionEnd = suspensionEnd;
    }

    public BotUser(long userId){
        this(userId, BotRole.DEFAULT, 0, SuspensionCode.UNSUSPENDED, 0L);
    }

    /**
     * Updates the database to reflect the changes made to this object.
     * Should be called to save any changes made to the user (role, times used, etc.)
     *
     * @param db Mastr DatabaseManager
     */
    public void set(DatabaseManager db){
        db.setBotUser(this);
    }

    public long getUserId() {
        return userId;
    }

    public BotRole getRole() {
        return role;
    }

    public void setRole(BotRole role) {
        this.role = role;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public void incrementTimesUsed(){
        timesUsed++;
    }

    public SuspensionCode getSuspensionCode() {
        return suspensionCode;
    }

    public void setSuspensionCode(SuspensionCode suspensionCode) {
        this.suspensionCode = suspensionCode;
    }

    public long getSuspensionEnd() {
        return suspensionEnd;
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
                ", suspensionCode=" + suspensionCode +
                ", suspensionEnd=" + suspensionEnd +
                '}';
    }
}
