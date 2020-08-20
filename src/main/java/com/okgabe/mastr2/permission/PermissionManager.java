/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.permission;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.util.SuspensionCode;
import com.okgabe.mastr2.util.TimeUtil;

public class PermissionManager {
    private Mastr mastr;

    public PermissionManager(Mastr mastr) {
        this.mastr = mastr;
    }

    public boolean isBannedUser(long id){
        return isBannedUser(mastr.getDatabaseManager().getBotUser(id));
    }

    public boolean isBannedUser(BotUser user){
        if(user.getSuspensionCode() == SuspensionCode.PERMANENT_SUSPENSION){
            return true;
        }
        else if(user.getSuspensionCode() == SuspensionCode.AUTOMATIC_SUSPENSION || user.getSuspensionCode() == SuspensionCode.TEMPORARY_SUSPENSION){
            long now = TimeUtil.getNow();
            long unsuspension = user.getSuspensionEnd();

            if(now >= unsuspension){
                user.setSuspensionCode(SuspensionCode.UNSUSPENDED);
                user.setSuspensionEnd(0L);
                user.set(mastr.getDatabaseManager());
                return false;
            }
            else return true;
        }
        else return false;
    }

    public boolean isBannedGuild(long id){
        return isBannedGuild(mastr.getDatabaseManager().getBotGuild(id));
    }

    public boolean isBannedGuild(BotGuild guild){
        if(guild.getSuspensionCode() == SuspensionCode.PERMANENT_SUSPENSION || guild.getSuspensionCode() == SuspensionCode.AUTOMATIC_SUSPENSION || guild.getSuspensionCode() == SuspensionCode.TEMPORARY_SUSPENSION){
            return true;
        }
        else return false;
    }
}
