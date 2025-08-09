package com.weg.infoweg.infrastructure.validator.util;

import com.weg.infoweg.infrastructure.validator.EmailWegValidator;
import com.weg.infoweg.modules.user.domain.ports.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorUtil {
    private static final EmailValidator VALIDATOR_WEG = new EmailWegValidator();

    public static EmailValidator toInstanceValidatorWeg(){
        return VALIDATOR_WEG;
    }
}
