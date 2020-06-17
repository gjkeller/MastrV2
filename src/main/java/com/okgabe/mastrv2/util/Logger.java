/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastrv2.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private LoggerStatus status;
    DateTimeFormatter dtf;

    public Logger(LoggerStatus status){
        this.status = status;
        dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
    }

    public void critical(Object object){
        if(LogType.CRITICAL.getLevel() <= status.getLevel()){
            System.out.println("[" + getTime() + "] [CRITICAL] " + object);
        }
    }

    public void warn(Object object){
        if(LogType.WARN.getLevel() <= status.getLevel()){
            System.out.println("[" + getTime() + "] [WARN] " + object);
        }
    }

    public void info(Object object){
        if(LogType.INFO.getLevel() <= status.getLevel()){
            System.out.println("[" + getTime() + "] [INFO] " + object);
        }
    }

    public void debug(Object object){
        if(LogType.DEBUG.getLevel() <= status.getLevel()){
            System.out.println("[" + getTime() + "] [DEBUG] " + object);
        }
    }

    public void waste(Object object){
        if(LogType.WASTE.getLevel() <= status.getLevel()){
            System.out.println("[" + getTime() + "] [WASTE] " + object);
        }
    }

    public String getTime(){
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void setStatus(LoggerStatus status){
        if(status==null) return;
        this.status = status;
    }
}
