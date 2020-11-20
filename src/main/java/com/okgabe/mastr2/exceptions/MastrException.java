/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.exceptions;

/**
 * Class for safely throwing exceptions that can be displayed publicly to users.
 * Messages here will be printed as output to the user if an error occurs during the running of a command.
 */
public class MastrException extends RuntimeException {
    public MastrException(String message){
        super(message);
    }

    public MastrException(String message, Throwable cause){
        super(message, cause);
    }
}
