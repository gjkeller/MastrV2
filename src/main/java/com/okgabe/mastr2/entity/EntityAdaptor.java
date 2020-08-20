/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.entity;

import com.mongodb.lang.Nullable;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.GuildTier;
import org.bson.Document;

public class EntityAdaptor {
    public static BotUser toBotUser(@Nullable Document document){
        if(document == null)
            return new BotUser(0L, BotRole.UNKNOWN, 0, 0);
        else
            return new BotUser(document.getLong("_id"), BotRole.parse(document.getInteger("roleId")),
                document.getInteger("timesUsed"), document.getInteger("suspensionEnd"));
    }

    public static Document fromBotUser(BotUser botUser){
        Document newDoc = new Document();
        newDoc.put("_id", botUser.getUserId());
        newDoc.put("roleId", botUser.getRole().getLevel());
        newDoc.put("timesUsed", botUser.getTimesUsed());
        newDoc.put("suspensionEnd", botUser.getSuspensionEnd());

        return newDoc;
    }

    public static BotGuild toBotGuild(@Nullable Document document){
        if(document == null)
            return new BotGuild(0L, "", 0, GuildTier.UNKNOWN);
        else
            return new BotGuild(document.getLong("_id"), document.getString("prefix"), document.getInteger("timesUsed"), GuildTier.parse(document.getInteger("guildTier")));
    }

    public static Document fromBotGuild(BotGuild botGuild){
        Document newDoc = new Document();
        newDoc.put("_id", botGuild.getGuildId());
        newDoc.put("prefix", botGuild.getPrefix());
        newDoc.put("timesUsed", botGuild.getTimesUsed());
        newDoc.put("guildTier", botGuild.getGuildTier().getLevel());

        return newDoc;
    }
}
