package com.weg.infoweg.infrastructure.persistence.user.mapper;

import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;

public class UserRegisterMapper {


    public User toEntity(UserRegisterRequest userRegisterRequest){
        return new User( userRegisterRequest.username(), userRegisterRequest.email(), userRegisterRequest.password(), userRegisterRequest.phoneNumber(), AccessLevel.STUDENT);
    }


}
