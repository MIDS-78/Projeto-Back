package com.weg.infoweg.modules.auth.domain.exceptions;

import com.weg.infoweg.modules.user.domain.exceptions.DomainException;

public class PasswordValidException extends DomainException {
    public PasswordValidException(String message) {
        super(message);
    }
}
