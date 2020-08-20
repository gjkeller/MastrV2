/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.db;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.entity.EntityAdaptor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {

    private MongoClient client;
    private MongoDatabase mastrDatabase;
    private MongoCollection<Document> users;
    private MongoCollection<Document> guilds;
    private static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    public DatabaseManager(String connectionString) throws MongoException  {
        client = MongoClients.create(connectionString);
        client.startSession();
        mastrDatabase = client.getDatabase("mastr");
        users = mastrDatabase.getCollection("users");
        guilds = mastrDatabase.getCollection("guilds");
    }

    public BotUser getBotUser(long id){
        return getBotUser(id, true);
    }

    public BotUser getBotUser(long id, boolean makeIfNotExist){
        Document search = new Document();
        search.put("_id", id);

        FindIterable<Document> userIter = users.find(search);
        Document user = userIter.cursor().tryNext();

        if(makeIfNotExist && user == null){
            return createBotUser(id);
        }

        return EntityAdaptor.toBotUser(user);
    }

    public BotGuild getBotGuild(long id){
        Document search = new Document();
        search.put("_id", id);

        FindIterable<Document> guildIter = guilds.find(search);
        Document guild = guildIter.cursor().tryNext();

        return EntityAdaptor.toBotGuild(guild);
    }

    public void setBotUser(BotUser botUser){
        Document oldUserSearch = new Document();
        oldUserSearch.put("_id", botUser.getUserId());

        users.updateOne(oldUserSearch, EntityAdaptor.fromBotUser(botUser));
    }

    public void setBotGuild(BotGuild botGuild){
        Document oldGuildSearch = new Document();
        oldGuildSearch.put("_id", botGuild.getGuildId());

        users.updateOne(oldGuildSearch, EntityAdaptor.fromBotGuild(botGuild));
    }

    public BotUser createBotUser(long id){
        BotUser botUser = new BotUser(id);
        users.insertOne(EntityAdaptor.fromBotUser(botUser));

        return botUser;
    }

    public String getGuildPrefix(long id){
        Document search = new Document();
        search.put("_id", id);

        FindIterable<Document> guildIter = guilds.find(search);
        Document guild = guildIter.cursor().tryNext();

        if(guild==null) return null;
        else return guild.getString("prefix");
    }
}
