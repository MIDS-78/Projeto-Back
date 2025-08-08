package com.weg.infoweg.modules.auth.aplication.port;


import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;

public interface AuthService {

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

}
