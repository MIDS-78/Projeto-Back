package com.weg.infoweg.modules.user.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super("Invalid password: " + message);
    }
}