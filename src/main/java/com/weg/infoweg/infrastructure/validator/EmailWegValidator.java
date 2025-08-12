package com.weg.infoweg.infrastructure.validator;

import com.weg.infoweg.modules.user.domain.ports.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailWegValidator implements EmailValidator {
    private static final String REGEX_EMAIL_WEG = "^[a-zA-Z0-9._%+-]+@weg\\.net$";
    private static final Pattern PADRAO = Pattern.compile(REGEX_EMAIL_WEG);

    @Override
    public boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = PADRAO.matcher(email);
        return matcher.matches();
    }
}
