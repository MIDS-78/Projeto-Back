package com.weg.infoweg.modules.auth.domain.exceptions;

import jakarta.validation.ValidationException;

public class EmailInvalidException extends ValidationException {
    public EmailInvalidException(String message) {
        super(message);
    }
}
