package com.weg.infoweg.modules.user.exceptions;

public class InvalidUsernameException extends DomainException {
    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}
