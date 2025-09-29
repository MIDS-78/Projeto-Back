package com.weg.infoweg.modules.auth.domain.service;

import com.weg.infoweg.modules.auth.domain.cases.*;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final LoginUserCase loginUserCase;

    private final RegisterUserCase registerUserCase;

    private final LogoutUserCase logoutUserCase;

    private final RefreshUserCase refreshUserCase;

    private final GetUserAuthenticationCase getUserAuthenticationCase;

    public AuthServiceImpl(LoginUserCase loginUserCase, RegisterUserCase registerUserCase, LogoutUserCase logoutUserCase, RefreshUserCase refreshUserCase, GetUserAuthenticationCase getUserAuthenticationCase) {
        this.loginUserCase = loginUserCase;
        this.registerUserCase = registerUserCase;
        this.logoutUserCase = logoutUserCase;
        this.refreshUserCase = refreshUserCase;
        this.getUserAuthenticationCase = getUserAuthenticationCase;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        JwtTokenDto jwtTokenDto = loginUserCase.execute(userLoginRequest);
        return new UserLoginResponse(jwtTokenDto);
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        return registerUserCase.execute(userRegisterRequest);
    }

    @Override
    public void logout(UUID id, JwtTokenDto jwtTokenDto) {
        logoutUserCase.execute(id, jwtTokenDto);
    }

    @Override
    public JwtTokenDto refresh(UUID id, JwtTokenDto jwtTokenDto) {
        return refreshUserCase.execute(id, jwtTokenDto);
    }

    @Override
    public UserGetResponse getUserAuthentication(UUID id) {
        return getUserAuthenticationCase.execute(id);
    }
}
