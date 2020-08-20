/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command;

public enum CommandCategory {
    MODERATION("Moderation", "Moderating your server", 0),
    FUN("Fun", "Fun commands for when you're bored",  2),
    MISC("Miscellaneous", "Other random commands", 3),
    UTIL("Utilities", "Commands that make your life easier", 4),
    MASTR("Mastr", "Commands related to Mastr",  5),
    MASTR_ADMIN("Administrative", "Commands only for bot admins", 6);

    private String name;
    private String description;
    private int id;

    CommandCategory(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}