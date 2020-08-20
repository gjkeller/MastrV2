/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

public enum SuspensionCode {
    UNSUSPENDED("Unsuspended", 0), AUTOMATIC_SUSPENSION("Automatic Suspension", 10), TEMPORARY_SUSPENSION("Temporary Suspension", 20),
    PERMANENT_SUSPENSION("Permanent Suspension", 30);

    private final String name;
    private final int code;

    SuspensionCode(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static SuspensionCode parse(String name){
        String n = name.toLowerCase();
        for(SuspensionCode suscode : values()){
            if(suscode.getName().toLowerCase().equals(n)) return suscode;
        }

        return UNSUSPENDED;
    }

    public static SuspensionCode parse(int code){
        for(SuspensionCode suscode : values()){
            if(suscode.getCode() == code) return suscode;
        }

        return UNSUSPENDED;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
