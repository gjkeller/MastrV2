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
import org.bson.Document;

public class EntityAdaptor {
    public static BotUser toBotUser(@Nullable Document document){
        if(document == null)
            return new BotUser(0L, BotRole.UNKNOWN, 0, 0);
        else
            return new BotUser(document.getLong("_id"), BotRole.parse(document.getInteger("roleId")),
                document.getInteger("timesUsed"), document.getInteger("suspensionLength"));
    }

    public static Document fromBotUser(BotUser botUser){
        Document newDoc = new Document();
        newDoc.put("_id", botUser.getUserId());
        newDoc.put("roleId", botUser.getRole().getLevel());
        newDoc.put("timesUsed", botUser.getTimesUsed());
        newDoc.put("suspensionLength", botUser.getSuspensionLength());

        return newDoc;
    }
}
