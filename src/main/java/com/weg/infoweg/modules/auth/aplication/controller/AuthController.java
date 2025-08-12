package com.weg.infoweg.modules.auth.aplication.controller;

import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApiDto<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserLoginResponse>("success", "Login completed successfully", userLoginResponse, LocalDateTime.now()));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDto<UserRegisterResponse>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        UserRegisterResponse userRegisterResponse = authService.register(userRegisterRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserRegisterResponse>("success", "User registered successfully", userRegisterResponse, LocalDateTime.now()));
    }

}
