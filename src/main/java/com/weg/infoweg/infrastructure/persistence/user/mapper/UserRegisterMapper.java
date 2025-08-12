package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.infrastructure.validator.util.EmailValidatorUtil;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterMapper {


    public User toEntity(UserRegisterRequest userRegisterRequest){
        final Email email = new Email(userRegisterRequest.email(), EmailValidatorUtil.toInstanceValidatorWeg());
        return new User( userRegisterRequest.username(), email, userRegisterRequest.password(), userRegisterRequest.phoneNumber(), AccessLevel.STUDENT);
    }


}
