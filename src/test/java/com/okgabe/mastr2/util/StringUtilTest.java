/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void join() {
        assertEquals(StringUtil.join(new String[] {"arg1", "arg2", "arg3"}), "arg1 arg2 arg3");
        assertEquals(StringUtil.join(new String[] {"arg1 arg2", ""}), "arg1 arg2");
        assertEquals(StringUtil.join(new String[] {"arg1", null, "arg2", ""}), "arg1 arg2");
        assertEquals(StringUtil.join(new String[0]), "");
    }

    @Test
    void testJoin() {
        assertEquals(StringUtil.join(new String[] {"arg1", "arg2"}, ","), "arg1,arg2");
    }

    @Test
    void testJoin1() {
        assertEquals(StringUtil.join(new String[] {"arg1", "arg2", "arg3"}, 1), "arg2 arg3");

    }

    @Test
    void testJoin2() {
        assertEquals(StringUtil.join(new String[] {"arg1", "arg2", "arg3"}, ",", 1), "arg2,arg3");
    }

    @Test
    void isNumeric() {
        assertTrue(StringUtil.isNumeric("5"));
        assertTrue(StringUtil.isNumeric("5531758912754108745732480714531435"));
        assertFalse(StringUtil.isNumeric("fsdaf"));
        assertFalse(StringUtil.isNumeric(""));
        assertFalse(StringUtil.isNumeric("f51a"));
        assertFalse(StringUtil.isNumeric(null));
        assertFalse(StringUtil.isNumeric("-"));
    }

    @Test
    void positionInAlphabet() {
        assertEquals(StringUtil.positionInAlphabet('a'), 0);
        assertEquals(StringUtil.positionInAlphabet('h'), 7);
        assertEquals(StringUtil.positionInAlphabet('H'), 7);
    }

    @Test
    void numberToAlphabet() {
        assertEquals(StringUtil.numberToAlphabet(1), 'b');
        assertEquals(StringUtil.numberToAlphabet(0), 'a');
        assertEquals(StringUtil.numberToAlphabet(25), 'z');
    }

    @Test
    void numberToString() {
        assertEquals(StringUtil.numberToString(4), "four");
        assertEquals(StringUtil.numberToString(1), "one");
    }
}