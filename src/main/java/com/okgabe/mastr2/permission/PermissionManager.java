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
        return guild.getSuspensionCode() == SuspensionCode.PERMANENT_SUSPENSION || guild.getSuspensionCode() == SuspensionCode.AUTOMATIC_SUSPENSION || guild.getSuspensionCode() == SuspensionCode.TEMPORARY_SUSPENSION;
    }

    public boolean suspend(long id, SuspensionCode code, long suspensionEnd){
        return suspend(mastr.getDatabaseManager().getBotUser(id), code, suspensionEnd);
    }

    /**
     * Permanently suspends the given user
     *
     * @param id User ID
     * @return True if successful, false if already suspended
     */
    public boolean suspend(long id){
        return suspend(mastr.getDatabaseManager().getBotUser(id), SuspensionCode.PERMANENT_SUSPENSION, 0);
    }

    /**
     * Permanently suspends the given user
     *
     * @param user User
     * @return True if successful, false if already suspended
     */
    public boolean suspend(BotUser user){
        return suspend(user, SuspensionCode.PERMANENT_SUSPENSION, 0);
    }

    /**
     * Suspends the given user, will override temporary suspensions
     *
     * @param user User
     * @param code Suspension code
     * @param suspensionEnd Time in which the suspension ends
     * @return True if successful, false if already suspended
     * @throws IllegalArgumentException If code is SuspensionCode.UNSUSPENDED - utilize {@link PermissionManager#unsuspend} instead
     */
    public boolean suspend(BotUser user, SuspensionCode code, long suspensionEnd) throws IllegalArgumentException {
        if(code == SuspensionCode.UNSUSPENDED) throw new IllegalArgumentException("Provided SuspensionCode.UNSUSPENDED: use PermissionManager#unsuspend instead of this operation!");
        if(user.getSuspensionCode() == SuspensionCode.PERMANENT_SUSPENSION) return false;

        user.setSuspensionCode(code);
        user.setSuspensionEnd(suspensionEnd);
        user.set(mastr.getDatabaseManager());
        return true;
    }

    /**
     * Unsuspends the given user.
     *
     * @param id User ID
     * @return True if successful, false if not suspended
     */
    public boolean unsuspend(long id){
        return unsuspend(mastr.getDatabaseManager().getBotUser(id));
    }

    /**
     * Unsuspends the given user.
     *
     * @param user User
     * @return True if successful, false if not suspended
     */
    public boolean unsuspend(BotUser user){
        if(!isBannedUser(user)) return false;
        user.setSuspensionEnd(0L);
        user.setSuspensionCode(SuspensionCode.UNSUSPENDED);
        user.set(mastr.getDatabaseManager());
        return true;
    }
}
