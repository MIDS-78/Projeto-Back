package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.infrastructure.validator.util.EmailValidatorUtil;
import com.weg.infoweg.modules.user.application.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.application.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserUpdateMapper {

    public void toEntity(UserUpdateRequest userUpdateRequest, User user){
        final Email email = new Email(userUpdateRequest.email(), EmailValidatorUtil.toInstanceValidatorWeg());

        user.setUsername(userUpdateRequest.name());
        user.setEmail(email);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user.getId());
    }

    public UserUpdateResponse toResponse(User user){
        return new UserUpdateResponse(user.getId(), user.getUsername(), user.getPasswordHash());
    }

}
