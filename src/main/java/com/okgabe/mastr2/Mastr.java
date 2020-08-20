/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.MongoException;
import com.okgabe.mastr2.cache.CacheManager;
import com.okgabe.mastr2.command.CommandHandler;
import com.okgabe.mastr2.db.DatabaseManager;
import com.okgabe.mastr2.dm.DirectMessageHandler;
import com.okgabe.mastr2.event.EventManager;
import com.okgabe.mastr2.permission.PermissionManager;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.Checks;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Mastr extends ListenerAdapter {

    private static String VERSION;
    private static final Logger logger = LoggerFactory.getLogger(Mastr.class);

    private ShardManager shardManager;
    private EventManager eventManager;
    private DatabaseManager databaseManager;
    private PermissionManager permissionManager;
    private DirectMessageHandler directMessageHandler;
    private CommandHandler commandHandler;
    private CacheManager cacheManager;

    public static void main(String[] args) {
        System.out.println("Starting up Mastr...");
        System.out.println("Loading settings from configuration file...");

        /* LOAD CONFIGURATION FILE */
        Reader configReader;
        JsonObject file;
        try{
            File configurationFile = new File("config.hjson");
            configReader = new FileReader(configurationFile);
            file = JsonObject.readHjson(configReader).asObject();
        }
        catch(IOException ex){
            System.err.println("Failed to find configuration file. Please leave \"config.hjson\" in the same directory as the bot's .jar file. Refer to the GitHub page," +
                    " https://github.com/ItsGJK/MastrV2, for more information.");
            System.exit(-1);
            return; // To shut up the IDEs
        }

        /* GET LOGGER MODE */
        Level logMode = Level.toLevel(checkValue(file.getString("log mode", "null"), "log mode").toUpperCase(), Level.INFO);
        if(logMode == null){
            System.err.println("Malformed configuration file for \"log mode\"!" +
                    " Refer to the default configuration file found at the GitHub page https://github.com/ItsGJK/MastrV2.");
            System.exit(-1);
            return;
        }

        System.out.println("Configuring logger...");
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger mastrLogger = loggerContext.getLogger("com.okgabe.mastr2");
        mastrLogger.setLevel(logMode);

        logger.info("Configured!");


        /* LOAD BOT MODE */
        BotRole botMode = BotRole.parse(checkValue(file.getString("bot mode", "null"), "bot mode"));
        if(botMode == null){
            logger.error("Malformed configuration file for \"bot mode\"!" +
                    " Refer to the default configuration file found at the GitHub page https://github.com/ItsGJK/MastrV2.");
            System.exit(-1);
            return;
        }

        /* GET BOT VERSION */
        VERSION = checkValue(file.getString("version", "null"), "version");
        if(Checks.isNullString(VERSION)){
            logger.error("Malformed configuration file for \"version\"!" +
                    " Refer to the default configuration file found at the GitHub page https://github.com/ItsGJK/MastrV2.");
            System.exit(-1);
            return;
        }

        /* Other strings and info */
        String dbConnectionString = checkValue(file.getString("mongodb connection string", "null"), "mongodb connection string");
        String token = checkValue(file.getString("bot token", "null"), "bot token");

        Iterator<JsonValue> managerIter = file.get("managers").asArray().iterator();
        List<String> managerList = new ArrayList<>();

        while(managerIter.hasNext()){
            JsonValue val = managerIter.next();
            if(val.isString()){
                managerList.add(val.asString());
            }
        }

        new Mastr(token, dbConnectionString, botMode, managerList);
    }

    private Mastr(String token, String dbConnectionString, BotRole botMode, Collection<String> managers){
        logger.info("Connecting to database...");
        try{
            databaseManager = new DatabaseManager(dbConnectionString);
        }
        catch(MongoException ex){
            logger.error("Unable to log into the database! Please provide a valid connection-string.");
            ex.printStackTrace();
            System.exit(-1);
        }

        logger.info("Connection successful. Authentication will begin on the next transaction.");
        logger.info("Starting the bot...");
        try{
            DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
            EventManager eventManager = new EventManager(this);
            builder.addEventListeners(this, eventManager);
            shardManager = builder.build();
        }
        catch(LoginException ex){
            logger.error("Invalid token provided! Ensure you provide a valid bot token in the configuration file.");
        }

        cacheManager = new CacheManager(this);
    }

    @Override
    public void onReady(ReadyEvent e){
        JDA.ShardInfo shardInfo = e.getJDA().getShardInfo();
        logger.info("Shard #" + shardInfo.getShardId() + "/" + shardInfo.getShardTotal() + " started.");
        logger.info("Available guilds on this shard: " + e.getGuildAvailableCount() + "/" + e.getGuildTotalCount() + " (" + e.getGuildUnavailableCount() + " unavailable)");

        // Load up bot on first shard's completion
        if(shardInfo.getShardId()==0){
            permissionManager = new PermissionManager(this);
            directMessageHandler = new DirectMessageHandler(this);
            commandHandler = new CommandHandler(this);
        }
    }

    private static String checkValue(String value, String valueName){
        if(value.equals("null")){
            logger.error("Malformed configuration file for \"" + valueName + "\"! " +
                    "Refer to the default configuration file found at the GitHub page https://github.com/ItsGJK/MastrV2.");
            System.exit(-1);
            return "";
        }
        else{
            return value;
        }
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public DirectMessageHandler getDirectMessageHandler() {
        return directMessageHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
