package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidIdException extends DomainException {
    public InvalidIdException(String message) {
        super("Invalid ID: " + message);
    }
}
