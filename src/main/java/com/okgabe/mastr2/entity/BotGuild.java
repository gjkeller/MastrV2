/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.entity;

import com.okgabe.mastr2.util.GuildTier;

public class BotGuild {
    private long guildId;
    private String prefix;
    private int timesUsed;
    private GuildTier guildTier;

    public BotGuild(long guildId, String prefix, int timesUsed, GuildTier guildTier) {
        this.guildId = guildId;
        this.prefix = prefix;
        this.timesUsed = timesUsed;
        this.guildTier = guildTier;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
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

    public GuildTier getGuildTier() {
        return guildTier;
    }

    public void setGuildTier(GuildTier guildTier) {
        this.guildTier = guildTier;
    }

    @Override
    public String toString() {
        return "BotGuild{" +
                "guildId=" + guildId +
                ", prefix='" + prefix + '\'' +
                ", timesUsed=" + timesUsed +
                ", guildTier=" + guildTier +
                '}';
    }
}
