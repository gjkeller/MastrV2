/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksTest {

    @Test
    void isEmptyString() {
        assertFalse(Checks.isEmptyString("test"));
        assertTrue(Checks.isEmptyString(""));
        assertTrue(Checks.isEmptyString(" "));
        assertTrue(Checks.isEmptyString("null"));
        assertTrue(Checks.isEmptyString(null));
    }

    @Test
    void isId() {
        assertTrue(Checks.isId("196834326963290112"));
        assertTrue(Checks.isId("403360290395389952"));
        assertFalse(Checks.isId("not an id"));
        assertFalse(Checks.isId("12345"));
        assertFalse(Checks.isId("6490276907340689731046"));
        assertFalse(Checks.isId(null));
    }
}