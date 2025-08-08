package com.weg.infoweg.infrastructure.security.password;

import com.weg.infoweg.modules.auth.domain.exceptions.PasswordValidException;
import com.weg.infoweg.modules.auth.domain.ports.PasswordValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidatorImpl implements PasswordValidator {

    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d");
    private static final Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]");

    @Override
    public boolean isValid(String password) {
        validateLength(password);
        validateSpecialCharacter(password);
        validateNumber(password);
        validateLetter(password);
        return true;
    }

    private void validateLength(String password) {
        if (password == null || password.length() < 8) {
            throw new PasswordValidException("Password must be at least 8 characters long");
        }
    }

    private void validateSpecialCharacter(String password) {
        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new PasswordValidException("Password must contain at least one special character");
        }
    }

    private void validateNumber(String password) {
        if (!NUMBER_PATTERN.matcher(password).find()) {
            throw new PasswordValidException("Password must contain at least one digit");
        }
    }

    private void validateLetter(String password) {
        if (!LETTER_PATTERN.matcher(password).find()) {
            throw new PasswordValidException("Password must contain at least one letter");
        }
    }
}
