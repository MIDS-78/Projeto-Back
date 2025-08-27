package com.weg.infoweg.modules.auth.aplication.port;


import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;

import java.util.UUID;

public interface AuthService {

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    void logout(UUID id, JwtTokenDto jwtTokenDto);

    JwtTokenDto refresh(UUID id, JwtTokenDto jwtTokenDto);

    UserGetResponse getUserAuthentication(UUID uuid);
}
