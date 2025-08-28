package com.weg.infoweg.modules.user.application.port;

import com.weg.infoweg.modules.user.application.dtos.*;

import java.util.UUID;


public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest, UUID id);

    void deleteUser(UserDeleteRequest userDeleteRequest);

    UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest, UUID id);

    UserGetResponse getUser(UserGetRequest userGetRequest);

}
