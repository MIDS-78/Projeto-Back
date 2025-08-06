package com.weg.infoweg.modules.user.exceptions;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super("Invalid ID: " + message);
    }
}
