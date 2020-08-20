/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.entity;

import com.okgabe.mastr2.db.DatabaseManager;
import com.okgabe.mastr2.util.GuildTier;
import com.okgabe.mastr2.util.SuspensionCode;

public class BotGuild {
    private final long guildId;
    private String prefix;
    private int timesUsed;
    private GuildTier guildTier;
    private SuspensionCode suspensionCode;

    public BotGuild(long guildId, String prefix, int timesUsed, GuildTier guildTier, SuspensionCode suspensionCode) {
        this.guildId = guildId;
        this.prefix = prefix;
        this.timesUsed = timesUsed;
        this.guildTier = guildTier;
        this.suspensionCode = suspensionCode;
    }

    public BotGuild(long guildId){
        this(guildId, "mas ", 0, GuildTier.DEFAULT, SuspensionCode.UNSUSPENDED);
    }

    /**
     * Updates the database to reflect the changes made to this object.
     * Should be called to save any changes made to the guild (tier, times used, etc.)
     *
     * @param db Mastr DatabaseManager
     */
    public void set(DatabaseManager db){
        db.setBotGuild(this);
    }

    public long getGuildId() {
        return guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public GuildTier getGuildTier() {
        return guildTier;
    }

    public void setGuildTier(GuildTier guildTier) {
        this.guildTier = guildTier;
    }

    public SuspensionCode getSuspensionCode() {
        return suspensionCode;
    }

    public void setSuspensionCode(SuspensionCode suspensionCode) {
        this.suspensionCode = suspensionCode;
    }

    @Override
    public String toString() {
        return "BotGuild{" +
                "guildId=" + guildId +
                ", prefix='" + prefix + '\'' +
                ", timesUsed=" + timesUsed +
                ", guildTier=" + guildTier +
                ", suspensionCode=" + suspensionCode +
                '}';
    }
}
