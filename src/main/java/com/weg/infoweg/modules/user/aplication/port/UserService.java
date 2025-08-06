package com.weg.infoweg.modules.user.aplication.port;

import com.weg.infoweg.modules.user.aplication.dtos.*;


public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest);

    UserDeleteResponse deleteUser(UserDeleteRequest userDeleteRequest);

    UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest);

    UserGetResponse getUser(UserGetRequest userGetRequest);

}
