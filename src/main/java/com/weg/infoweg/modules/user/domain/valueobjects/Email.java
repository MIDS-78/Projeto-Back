package com.weg.infoweg.modules.user.domain.valueobjects;

import com.weg.infoweg.modules.user.domain.exceptions.InvalidEmailException;
import com.weg.infoweg.modules.user.domain.ports.EmailValidator;

public class Email {

    private String address;

    public Email(String address, EmailValidator emailValidator){
        if(emailValidator.isValid(address)){
            throw new InvalidEmailException("The email entered does not belong to the '@weg.net' domain. Please enter a valid email.");
        }
        this.address = address;
    }
}
