package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.infrastructure.validator.util.EmailValidatorUtil;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;

public class UserCreateMapper {

    public User toEntity(UserCreateRequest userCreateRequest, String hashedPassword) {

        final User user = new User();
        final Email email = new Email(userCreateRequest.email(), EmailValidatorUtil.toInstanceValidatorWeg());

        user.setUsername(userCreateRequest.username());
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setPhoneNumber(userCreateRequest.phoneNumber());
        user.setAccessLevel(userCreateRequest.accessLevel());

        return user;
    }
}
