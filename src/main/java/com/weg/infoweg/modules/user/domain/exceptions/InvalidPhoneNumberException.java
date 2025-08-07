package com.weg.infoweg.modules.user.domain.exceptions;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String message) {
        super("Invalid phone number: " + message);
    }
}