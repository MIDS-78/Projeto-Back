package com.weg.infoweg.modules.user.exceptions;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String email) {
        super("Invalid email format: " + email);
    }
}
