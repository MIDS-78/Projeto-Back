package com.weg.infoweg.modules.auth.domain.exceptions;

public class AuthenticationValidationException extends RuntimeException {
    public AuthenticationValidationException(String message) {
        super(message);
    }
}
