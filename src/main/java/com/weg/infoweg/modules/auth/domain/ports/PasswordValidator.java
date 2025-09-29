package com.weg.infoweg.modules.auth.domain.ports;

public interface PasswordValidator {

    boolean isValid(String password);
}
