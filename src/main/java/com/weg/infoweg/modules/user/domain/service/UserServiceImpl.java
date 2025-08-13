package com.weg.infoweg.modules.user.domain.service;

import com.weg.infoweg.modules.user.aplication.dtos.*;
import com.weg.infoweg.modules.user.aplication.port.UserService;
import com.weg.infoweg.modules.user.domain.cases.CreateUserCase;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final CreateUserCase createUserCase;

    public UserServiceImpl(CreateUserCase createUserCase) {
        this.createUserCase = createUserCase;
    }


    @Override
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        return null;
    }

    @Override
    public UserDeleteResponse deleteUser(UserDeleteRequest userDeleteRequest) {
        return null;
    }

    @Override
    public UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public UserGetResponse getUser(UserGetRequest userGetRequest) {
        return null;
    }
}
