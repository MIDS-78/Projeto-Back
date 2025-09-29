package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidPhoneNumberException extends DomainException {
    public InvalidPhoneNumberException(String message) {
        super("Invalid phone number: " + message);
    }
}