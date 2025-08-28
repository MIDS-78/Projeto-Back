package com.weg.infoweg.modules.auth.application.port;


import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;

import java.util.UUID;

public interface AuthService {

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    void logout(UUID id, JwtTokenDto jwtTokenDto);

    JwtTokenDto refresh(UUID id, JwtTokenDto jwtTokenDto);
}
