package com.weg.infoweg.modules.auth.domain.exceptions;

public class PasswordValidException extends RuntimeException {
    public PasswordValidException(String message) {
        super(message);
    }
}
