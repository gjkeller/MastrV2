/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class MastrThreadFactory implements ThreadFactory {
    private int counter = 0;
    private String prefix;

    public MastrThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
    }
}
