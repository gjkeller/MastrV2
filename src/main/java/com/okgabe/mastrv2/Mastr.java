/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastrv2;

import com.okgabe.mastrv2.util.Logger;
import com.okgabe.mastrv2.util.LoggerStatus;
import org.hjson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Mastr {

    private static String version;
    private Logger logger;
    
    public static void main(String[] args) {
        new Mastr();
    }

    private Mastr(){
        logger = new Logger(LoggerStatus.DEBUG);
        logger.info("Starting up Mastr...");
        logger.info("Reading configuration file...");

        Reader configReader;
        JsonObject file;
        try{
            File configurationFile = new File("config.hjson");
            configReader = new FileReader(configurationFile);
            file = JsonObject.readHjson(configReader).asObject();
        }
        catch(IOException ex){
            logger.critical("Failed to find configuration file. Please leave \"config.hjson\" in the same directory as the bot's .jar file. Refer to the GitHub page," +
                    " https://github.com/ItsGJK/MastrV2, for more information.");
            System.exit(-1);
            return; // To shut up the IDEs
        }

        String loggerStatus = checkValue(file.getString("debug mode", "null"), "debug mode");
        logger.setStatus(LoggerStatus.parseStatus(loggerStatus));

        version = checkValue(file.getString("version", "null"), "version");

        String mysqlUsername = checkValue(file.getString("mysql username", "null"), "mysql username");
        String mysqlPassword = checkValue(file.getString("mysql password", "null"), "mysql password");


    }

    private String checkValue(String value, String valueName){
        if(value.equals("null")){
            logger.critical("Malformed configuration file for \"" + valueName + "! " +
                    "Refer to the default configuration file found at the GitHub page https://github.com/ItsGJK/MastrV2.");
            System.exit(-1);
            return "";
        }
        else{
            return value;
        }
    }
}
