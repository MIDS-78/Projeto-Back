package com.weg.infoweg.modules.user.domain.service;

import com.weg.infoweg.modules.user.application.dtos.*;
import com.weg.infoweg.modules.user.application.port.UserService;
import com.weg.infoweg.modules.user.domain.cases.CreateUserCase;
import com.weg.infoweg.modules.user.domain.cases.DeleteUserCase;
import com.weg.infoweg.modules.user.domain.cases.GetUserCase;
import com.weg.infoweg.modules.user.domain.cases.UpdateUserCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final CreateUserCase createUserCase;
    private final UpdateUserCase updateUserCase;
    private final DeleteUserCase deleteUserCase;
    private final GetUserCase getUserCase;

    public UserServiceImpl(CreateUserCase createUserCase, UpdateUserCase updateUserCase, DeleteUserCase deleteUserCase, GetUserCase getUserCase) {
        this.createUserCase = createUserCase;
        this.updateUserCase = updateUserCase;
        this.deleteUserCase = deleteUserCase;
        this.getUserCase = getUserCase;
    }


    @Override
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest, UUID id) {
        return createUserCase.execute(userCreateRequest, id);
    }

    @Override
    public void deleteUser(UserDeleteRequest userDeleteRequest) {
        deleteUserCase.execute(userDeleteRequest);
    }

    @Override
    public UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest,  UUID id) {
        return updateUserCase.execute(userUpdateRequest, id);
    }

    @Override
    public UserGetResponse getUser(UserGetRequest userGetRequest) {
        return getUserCase.execute(userGetRequest);
    }
}
