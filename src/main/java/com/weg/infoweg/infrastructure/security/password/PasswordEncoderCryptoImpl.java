package com.weg.infoweg.infrastructure.security.password;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import com.weg.infoweg.modules.auth.domain.ports.PasswordEncoderCrypto;

@Component
public class PasswordEncoderCryptoImpl implements PasswordEncoderCrypto {

    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String passwordAttempted, String password) {
        return BCrypt.checkpw(passwordAttempted, password);
    }

}