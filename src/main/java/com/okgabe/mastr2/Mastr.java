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
import com.okgabe.mastr2.event.EventManager;
import com.okgabe.mastr2.util.BotRole;
import com.okgabe.mastr2.util.Checks;
import net.dv8tion.jda.api.JDA;
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

public class Mastr {

    private static String VERSION;
    private static Logger logger = LoggerFactory.getLogger(Mastr.class);
    private ShardManager shardManager;
    private JDA jda;
    private EventManager eventManager;

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
        String dbUsername = checkValue(file.getString("mongodb username", "null"), "mongodb username");
        String dbPassword = checkValue(file.getString("mongodb password", "null"), "mongodb password");
        String token = checkValue(file.getString("bot token", "null"), "bot token");

        Iterator<JsonValue> managerIter = file.get("managers").asArray().iterator();
        List<String> managerList = new ArrayList<>();

        while(managerIter.hasNext()){
            JsonValue val = managerIter.next();
            if(val.isString()){
                managerList.add(val.asString());
            }
        }

        new Mastr(token, dbUsername, dbPassword, botMode, managerList);
    }

    private Mastr(String token, String dbUsername, String dbPassword, BotRole botMode, Collection<String> managers){
        logger.info("Starting the bot...");
        try{
            DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
            EventManager eventManager = new EventManager();
            builder.addEventListeners(eventManager);
            shardManager = builder.build();
        }
        catch(LoginException ex){
            logger.error("Invalid token provided! Ensure you provide a valid bot token in the configuration file.");
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
}
