/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

public class StringUtil {
    public static String join(String[] args){
        return join(args, " ", 0);
    }

    public static String join(String[] args, String combiner){
        return join(args, combiner, 0);
    }

    public static String join(String[] args, int start){
        return join(args, " ", start);
    }

    public static String join(String[] args, String combiner, int start){
        StringBuilder sb = new StringBuilder();
        for(int i = start; i < args.length; i++){
            sb.append(args[i])
            .append(combiner);
        }

        return sb.substring(0, sb.length()-combiner.length());
    }

    // From commons-lang StringUtils
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int positionInAlphabet(char c){
        c = Character.toLowerCase(c);
        if(c > 122) return -1;
        if(c < 97) return -1;
        return c - 97;
    }

    public static char numberToAlphabet(int n){
        return (char)(97+(n%25));
    }
    
    public static String numberToString(int i){
        switch(i){
            case 1:
                return  "one";
            case 2:
                return  "two";
            case 3:
                return  "three";
            case 4:
                return  "four";
            case 5:
                return  "five";
            case 6:
                return  "six";
            case 7:
                return  "seven";
            case 8:
                return  "eight";
            case 9:
                return "nine";
            default:
                return  "zero";
        }
    }
}
