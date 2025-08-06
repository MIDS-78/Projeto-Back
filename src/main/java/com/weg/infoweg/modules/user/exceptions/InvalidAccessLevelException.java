package com.weg.infoweg.modules.user.exceptions;

public class InvalidAccessLevelException extends RuntimeException {
    public InvalidAccessLevelException(String message) {
        super("Invalid access level: " + message);
    }
}
