package com.weg.infoweg.modules.auth.aplication.controller;

import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest);
        return ResponseEntity.ok(userLoginResponse);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        UserRegisterResponse userRegisterResponse = authService.register(userRegisterRequest);
        return ResponseEntity.ok(userRegisterResponse);
    }

}
