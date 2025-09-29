package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidDateException extends DomainException {
    public InvalidDateException(String message) {
        super("Invalid date: " + message);
    }
}
