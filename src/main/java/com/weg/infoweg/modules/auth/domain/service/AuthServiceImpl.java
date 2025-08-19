package com.weg.infoweg.modules.auth.domain.service;

import com.weg.infoweg.modules.auth.domain.cases.LogoutUserCase;
import com.weg.infoweg.modules.auth.domain.cases.RefleshUserCase;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import com.weg.infoweg.modules.auth.domain.cases.LoginUserCase;
import com.weg.infoweg.modules.auth.domain.cases.RegisterUserCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final LoginUserCase loginUserCase;

    private final RegisterUserCase registerUserCase;

    private LogoutUserCase logoutUserCase;

    private RefleshUserCase refleshUserCase;

    public AuthServiceImpl(LoginUserCase loginUserCase, RegisterUserCase registerUserCase) {
        this.loginUserCase = loginUserCase;
        this.registerUserCase = registerUserCase;
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
    public JwtTokenDto reflesh(UUID id, JwtTokenDto jwtTokenDto) {
        return refleshUserCase.execute(id, jwtTokenDto);
    }
}
