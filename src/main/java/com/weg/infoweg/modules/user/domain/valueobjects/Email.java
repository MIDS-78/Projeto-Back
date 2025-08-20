package com.weg.infoweg.modules.user.domain.valueobjects;

import com.weg.infoweg.modules.user.domain.exceptions.InvalidEmailException;
import com.weg.infoweg.modules.user.domain.ports.EmailValidator;

import java.util.Objects;

public class Email {

    private final String address;

    public Email(String address, EmailValidator emailValidator){
        if(address == null || address.trim().isEmpty() || !emailValidator.isValid(address)){
            throw new InvalidEmailException("The email entered does not belong to the '@weg.net' domain. Please enter a valid email.");
        }
        this.address = address;
    }

    public Email(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }
}
