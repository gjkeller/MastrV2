/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.dm;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.entity.BotUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DirectMessageHandler {
    private Mastr mastr;

    public DirectMessageHandler(Mastr mastr) {
        this.mastr = mastr;
    }

    public void handleMessage(MessageReceivedEvent e, BotUser user){

    }
}
