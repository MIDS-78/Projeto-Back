package com.weg.infoweg.modules.informative.domain.exceptions;

public class InformativeNotFoundException extends RuntimeException {
    public InformativeNotFoundException(String message) {
        super(message);
    }
}