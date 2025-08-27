package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.infrastructure.validator.util.EmailValidatorUtil;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserGetMapper {

    public UserGetResponse toResponse(User user){
        return new UserGetResponse(user.getId(), user.getUsername(), user.getEmail().toString(), user.getPhoneNumber(), user.getAccessLevel());
    }
}
