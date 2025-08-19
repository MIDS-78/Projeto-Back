package com.weg.infoweg.modules.auth.aplication.controller;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<ResponseApiDto<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserLoginResponse>("success", "Login completed successfully", userLoginResponse, LocalDateTime.now()));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApiDto<UserRegisterResponse>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        UserRegisterResponse userRegisterResponse = authService.register(userRegisterRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserRegisterResponse>("success", "User registered successfully", userRegisterResponse, LocalDateTime.now()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseApiDto<Void>> logout(@RequestBody JwtTokenDto jwtTokenDto){
        UUID id = userAuthenticationService.getIdUserAuthentication();
        authService.logout(id, jwtTokenDto);
        return ResponseEntity.ok(new ResponseApiDto<Void>("success", "User logged out successfully", LocalDateTime.now()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseApiDto<JwtTokenDto>> refresh(@RequestBody JwtTokenDto jwtTokenDto){
        UUID id = userAuthenticationService.getIdUserAuthentication();
        JwtTokenDto newToken = authService.refresh(id, jwtTokenDto);
        return ResponseEntity.ok(new ResponseApiDto<JwtTokenDto>("success", "User refresh successfully", newToken, LocalDateTime.now()));
    }


}
