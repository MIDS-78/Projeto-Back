package com.weg.infoweg.modules.user.domain.exceptions;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
