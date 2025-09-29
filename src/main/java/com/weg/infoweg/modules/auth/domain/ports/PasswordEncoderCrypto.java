package com.weg.infoweg.modules.auth.domain.ports;

public interface PasswordEncoderCrypto {

    public String encryptPassword(String password);

    public boolean checkPassword(String passwordAttempted, String password);
}
