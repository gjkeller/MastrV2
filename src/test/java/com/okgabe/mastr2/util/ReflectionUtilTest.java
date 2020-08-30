/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilTest {

    @Test
    void getClasses() {
        Set<Class<? extends CommandBase>> commands = ReflectionUtil.getClasses("com.okgabe.mastr2.command.commands");
        assertTrue(commands.size()>0, "No commands were found in the commands directory");
        for(Class clazz : commands){
            assertFalse(clazz.isAnonymousClass(), "No anonymous class should be a command");
            assertFalse(clazz.isMemberClass(), "No nested commands");
            assertFalse(clazz.getConstructors().length != 1, "Commands should only have one single constructor");
            assertEquals(clazz.getConstructors()[0].getParameterCount(),1, "Command constructors should only have one parameter");
            assertEquals(clazz.getConstructors()[0].getParameterTypes()[0], Mastr.class, "Command constructors should only have Mastr as their parameter");
        }
        System.out.println(commands.size() + " commands were found and validated");
    }
}