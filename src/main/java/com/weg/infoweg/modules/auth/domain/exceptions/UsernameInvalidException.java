package com.weg.infoweg.modules.auth.domain.exceptions;

import jakarta.validation.ValidationException;

public class UsernameInvalidException extends ValidationException {
    public UsernameInvalidException(String message) {
        super(message);
    }
}
