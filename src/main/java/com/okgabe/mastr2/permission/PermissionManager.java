/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.permission;

import com.okgabe.mastr2.Mastr;

public class PermissionManager {
    private Mastr mastr;

    public PermissionManager(Mastr mastr) {
        this.mastr = mastr;
    }

//    public boolean isBannedUser(long id){
//        return isBannedUser(mastr.getDatabaseManager().getBotUser(id));
//    }
//
//    public boolean isBannedUser(BotUser user){
//        if(user.getRole() == BotRole.PERMANENT_SUSPENSION){
//            return false;
//        }
//        else if(user.getRole() == BotRole.AUTOMATICALLY_SUSPENDED || user.getRole() == BotRole.TEMPORARY_SUSPENSION){
//
//        }
//    }
}
