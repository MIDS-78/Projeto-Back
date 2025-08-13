package com.weg.infoweg.modules.user.domain.service;

import com.weg.infoweg.modules.user.aplication.dtos.*;
import com.weg.infoweg.modules.user.aplication.port.UserService;
import com.weg.infoweg.modules.user.domain.cases.CreateUserCase;
import com.weg.infoweg.modules.user.domain.cases.DeleteUserCase;
import com.weg.infoweg.modules.user.domain.cases.UpdateUserCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final CreateUserCase createUserCase;
    private final UpdateUserCase updateUserCase;
    private final DeleteUserCase deleteUserCase;

    public UserServiceImpl(CreateUserCase createUserCase, UpdateUserCase updateUserCase, DeleteUserCase deleteUserCase) {
        this.createUserCase = createUserCase;
        this.updateUserCase = updateUserCase;
        this.deleteUserCase = deleteUserCase;
    }


    @Override
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest, UUID id) {
        return createUserCase.execute(userCreateRequest, id);
    }

    @Override
    public UserDeleteResponse deleteUser(UserDeleteRequest userDeleteRequest,  UUID id) {
        return deleteUserCase.execute(userDeleteRequest, id);
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest,  UUID id) {
        updateUserCase.execute(userUpdateRequest, id);
    }

    @Override
    public UserGetResponse getUser(UserGetRequest userGetRequest, UUID id) {
        return null;
    }
}
