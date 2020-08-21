/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
    public static long getNow(){
        return System.currentTimeMillis() / 1000;
    }

    public static long getNowMillis() { return System.currentTimeMillis(); }

    private static final Pattern timePattern = Pattern.compile("\\d+\\s*[a-z]+");
    private static final Pattern characterPattern = Pattern.compile("[a-z]+");
    private static final Pattern numberPattern = Pattern.compile("\\d+");
    public static int toSeconds(String time){
        time = time.toLowerCase();
        Matcher matcher = timePattern.matcher(time);
        int seconds = 0;

        while(matcher.find()){
            String match = matcher.group();
            int numbers;
            try{
                Matcher numbMatcher = numberPattern.matcher(match);
                numbMatcher.find();
                numbers = Integer.parseInt(numbMatcher.group());
            }
            catch(Exception ex){
                continue;
            }

            Matcher charMatcher = characterPattern.matcher(match);
            charMatcher.find();
            String chars = charMatcher.group();
            char firstChar = chars.charAt(0);

            if(firstChar=='s'){ //seconds
                seconds += numbers;
            }
            else if(chars.length()>1 && chars.substring(0, 2).equals("mo")){ //months
                seconds += numbers * 2592000;
            }
            else if(firstChar=='m'){ //minutes
                seconds += numbers * 60;
            }
            else if(firstChar=='h'){ //hours
                seconds += numbers * 3600;
            }
            else if(firstChar=='d'){ //days
                seconds += numbers * 86400;
            }
            else if(firstChar=='y'){ //years
                seconds += numbers * 31536000;
            }
        }
        
        return seconds;
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static String toStringShort(long seconds){
        double minutes = seconds / 60D;
        double hours = minutes / 60D;
        double days = hours / 24D;
        double years = days / 365D;

        if(60 > seconds) return seconds + " seconds";
        if(seconds == 60) return "1 minute";
        if(seconds == 3600) return "1 hour";
        if(seconds == 86400) return "1 day";

        if(seconds < 3600){
            return DECIMAL_FORMAT.format(minutes) + " minutes";
        }

        if(seconds < 86400){
            return DECIMAL_FORMAT.format(hours) + " hours";
        }

        if(seconds < 31556952){
            return DECIMAL_FORMAT.format(days) + " days";
        }

        if(seconds > 31556952){
            return DECIMAL_FORMAT.format(years) + " years";
        }

        return "null";
    }

    public static String toStringLong(long seconds){
        if(seconds<1) return "0 seconds";

        long days = seconds / 86400L;
        seconds = seconds - (days*86400L);

        long hours = seconds / 3600L;
        seconds = seconds - (hours*3600L);

        long minutes = seconds / 60L;
        seconds = seconds - (minutes*60L);

        long sec = seconds;

        StringBuilder sb = new StringBuilder();
        if(days>0){
            sb.append(days);
            if(days==1) sb.append(" day ");
            else sb.append(" days ");
        }
        if(hours>0){
            sb.append(hours);
            if(hours==1) sb.append(" hour ");
            else sb.append(" hours ");
        }
        if(minutes>0){
            sb.append(minutes);
            if(minutes==1) sb.append(" minute ");
            else sb.append(" minutes ");
        }
        if(sec>0){
            sb.append(sec);
            if(sec==1) sb.append(" second ");
            else sb.append(" seconds ");
        }

        return sb.substring(0,sb.length()-1);
    }
}
