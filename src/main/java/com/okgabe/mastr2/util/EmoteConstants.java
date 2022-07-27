/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import net.dv8tion.jda.api.JDA;

public class EmoteConstants {
    public static String MASTR_LOGO;
    public static String CHECK_SYMBOL;
    public static String X_SYMBOL;

    public static void updateEmotes(JDA jda){
        MASTR_LOGO = jda.getEmojiById(403741198155776001L).getAsMention();
        CHECK_SYMBOL = jda.getEmojiById(777322609284087850L).getAsMention();
        X_SYMBOL = jda.getEmojiById(777322609683071016L).getAsMention();
    }
}
