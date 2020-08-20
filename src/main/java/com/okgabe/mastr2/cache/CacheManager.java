/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.cache;

import com.okgabe.mastr2.Mastr;

import java.util.HashMap;

public class CacheManager {
    private Mastr mastr;
    private HashMap<Long, String> guildPrefixes;

    public CacheManager(Mastr mastr) {
        this.mastr = mastr;
        guildPrefixes = new HashMap<>();
    }

    public String getPrefix(long guildId){
        return guildPrefixes.get(guildId);
    }

    public void setPrefix(long guildId, String prefix){
        guildPrefixes.remove(guildId);
        guildPrefixes.put(guildId, prefix);
    }


}
