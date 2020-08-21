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
}
