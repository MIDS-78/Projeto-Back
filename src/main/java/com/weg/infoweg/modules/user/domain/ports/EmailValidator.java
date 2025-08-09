package com.weg.infoweg.modules.user.domain.ports;

public interface EmailValidator {

    boolean isValid(String address);
}
