package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.infrastructure.validator.util.EmailValidatorUtil;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;

import java.time.LocalDateTime;

public class UserUpdateMapper {

    public void toEntity(UserUpdateRequest userUpdateRequest, User user){
        final Email email = new Email(userUpdateRequest.email(), EmailValidatorUtil.toInstanceValidatorWeg());

        user.setUsername(userUpdateRequest.name());
        user.setEmail(email);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(1);
    }

}
