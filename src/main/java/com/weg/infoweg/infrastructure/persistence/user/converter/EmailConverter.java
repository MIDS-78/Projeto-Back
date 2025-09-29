package com.weg.infoweg.infrastructure.persistence.user.converter;

import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return (email != null) ? email.getAddress() : null;
    }

    @Override
    public Email convertToEntityAttribute(String address) {
        return (address != null) ? new Email(address) : null;
    }
}