package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.core.abstractions.UseCase;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;

public class CreateUserCase implements UseCase<UserCreateRequest, UserCreateResponse> {

    private UserRepository userRepository;

    @Override
    public UserCreateResponse execute(UserCreateRequest userCreateRequest) {
        return null;
    }
}
