package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidAccessLevelException extends RuntimeException {
    public InvalidAccessLevelException(String message) {
        super("Invalid access level: " + message);
    }
}
