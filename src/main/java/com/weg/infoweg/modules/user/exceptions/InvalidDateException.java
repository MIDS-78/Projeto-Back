package com.weg.infoweg.modules.user.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super("Invalid date: " + message);
    }
}
