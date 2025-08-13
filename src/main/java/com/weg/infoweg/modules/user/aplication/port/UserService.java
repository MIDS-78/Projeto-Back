package com.weg.infoweg.modules.user.aplication.port;

import com.weg.infoweg.modules.user.aplication.dtos.*;

import java.util.UUID;


public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest, UUID id);

    void deleteUser(UserDeleteRequest userDeleteRequest);

    UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest, UUID id);

    UserGetResponse getUser(UserGetRequest userGetRequest);

}
