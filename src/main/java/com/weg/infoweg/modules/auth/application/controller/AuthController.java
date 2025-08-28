package com.weg.infoweg.modules.auth.application.controller;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.application.port.AuthService;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserAuthenticationService userAuthenticationService;

    public AuthController(AuthService authService, UserAuthenticationService userAuthenticationService) {
        this.authService = authService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApiDto<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest);
        return ResponseEntity.ok(ResponseApiDto.success("Login completed successfully", userLoginResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDto<UserRegisterResponse>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse = authService.register(userRegisterRequest);
        return ResponseEntity.ok(ResponseApiDto.success("User registered successfully", userRegisterResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseApiDto<Void>> logout(@RequestBody JwtTokenDto jwtTokenDto) {
        UUID id = userAuthenticationService.getIdUserAuthentication();
        authService.logout(id, jwtTokenDto);
        return ResponseEntity.ok(ResponseApiDto.success("User logged out successfully"));
    }

    @PostMapping("/refresh")//
    public ResponseEntity<ResponseApiDto<JwtTokenDto>> refresh(@RequestBody JwtTokenDto jwtTokenDto) {
        UUID id = userAuthenticationService.getIdUserAuthentication();
        JwtTokenDto newToken = authService.refresh(id, jwtTokenDto);
        return ResponseEntity.ok(ResponseApiDto.success("User refreshed successfully", newToken));
    }
}