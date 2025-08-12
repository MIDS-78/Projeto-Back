package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException(String message) {
        super("Invalid password: " + message);
    }
}