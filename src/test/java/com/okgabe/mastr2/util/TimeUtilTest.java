/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilTest {

    @Test
    void toSeconds() {
        assertEquals(TimeUtil.toSeconds("1s"), 1);
        assertEquals(TimeUtil.toSeconds("1s 5m"), 301);
        assertEquals(TimeUtil.toSeconds("1d 6 hours 1 sec"), 108001);
        assertEquals(TimeUtil.toSeconds("2 month 1 year 6   h"), 36741600);
    }

    @Test
    void toStringShort() {
        assertEquals(TimeUtil.toStringShort(60), "1 minute");
        assertEquals(TimeUtil.toStringShort(120), "2 minutes");
        assertEquals(TimeUtil.toStringShort(121), "2.02 minutes");
        assertEquals(TimeUtil.toStringShort(1), "1 second");
        assertEquals(TimeUtil.toStringShort(2), "2 seconds");
        assertEquals(TimeUtil.toStringShort(0), "0 seconds");
    }

    @Test
    void toStringLong() {
        assertEquals(TimeUtil.toStringLong(1), "1 second");
        assertEquals(TimeUtil.toStringLong(2), "2 seconds");
        assertEquals(TimeUtil.toStringLong(60), "1 minute");
        assertEquals(TimeUtil.toStringLong(61), "1 minute 1 second");
        assertEquals(TimeUtil.toStringLong(62), "1 minute 2 seconds");
        assertEquals(TimeUtil.toStringLong(120), "2 minutes");
        assertEquals(TimeUtil.toStringLong(121), "2 minutes 1 second");
        assertEquals(TimeUtil.toStringLong(121), "2 minutes 1 second");
        assertEquals(TimeUtil.toStringLong(3600), "1 hour");
        assertEquals(TimeUtil.toStringLong(3601), "1 hour 1 second");
        assertEquals(TimeUtil.toStringLong(3661), "1 hour 1 minute 1 second");
        assertEquals(TimeUtil.toStringLong(3662), "1 hour 1 minute 2 seconds");
    }
}