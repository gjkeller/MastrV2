/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import net.dv8tion.jda.api.entities.Message;

import java.util.Collections;
import java.util.List;

public class MentionUtil {
    public static final List<Message.MentionType> NO_MENTIONS = Collections.emptyList();
    public static final List<Message.MentionType> USERS_ONLY = List.of(Message.MentionType.USER);
    public static final List<Message.MentionType> ROLES_ONLY = List.of(Message.MentionType.ROLE);
    public static final List<Message.MentionType> USERS_AND_ROLES = List.of(Message.MentionType.ROLE, Message.MentionType.USER);
}
