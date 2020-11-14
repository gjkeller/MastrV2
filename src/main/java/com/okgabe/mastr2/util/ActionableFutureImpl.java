/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ActionableFutureImpl<T> implements ActionableFuture<T> {

    private Consumer<? super Throwable> onFailure;
    private Consumer<T> onSuccess;

    @Override
    public ActionableFutureImpl<T> onError(@NotNull Consumer<? super Throwable> callback) {
        onFailure = callback;
        return this;
    }

    @Override
    public ActionableFutureImpl<T> onSuccess(@NotNull Consumer<T> success) {
        onSuccess = success;
        return this;
    }

    public Consumer<? super Throwable> getOnFailure() {
        return onFailure;
    }

    public Consumer<T> getOnSuccess() {
        return onSuccess;
    }
}
