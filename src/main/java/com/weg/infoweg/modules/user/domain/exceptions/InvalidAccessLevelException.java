package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidAccessLevelException extends DomainException {
    public InvalidAccessLevelException(String message) {
        super("Invalid access level: " + message);
    }
}
