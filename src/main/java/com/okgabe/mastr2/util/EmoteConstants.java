/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;

public class EmoteConstants {
    public static Emote MASTR_LOGO;
    public static Emote CHECK_SYMBOL;
    public static Emote X_SYMBOL;

    public static void updateEmotes(JDA jda){
        MASTR_LOGO = jda.getEmoteById(403741198155776001L);
        CHECK_SYMBOL = jda.getEmoteById(749187001512624149L);
        X_SYMBOL = jda.getEmoteById(749187001378406414L);
    }
}
