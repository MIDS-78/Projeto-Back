package com.weg.infoweg.modules.user.aplication.controller;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.user.aplication.dtos.*;
import com.weg.infoweg.modules.user.aplication.port.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;

    public UserController(UserService userService, UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping()
    public ResponseEntity<ResponseApiDto<UserGetResponse>> getUser(@PathVariable UserGetRequest userGetRequest) {
        UserGetResponse userGetResponse = userService.getUser(userGetRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserGetResponse>("200", "User get with success", userGetResponse, LocalDateTime.now()));
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        UUID id = userAuthenticationService.getIdUserAuthentication();
        UserCreateResponse response = userService.createUser(request, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApiDto<UserUpdateResponse>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {

        UserUpdateResponse userUpdateResponse = userService.updateUser(request, id);
        return ResponseEntity.ok(new ResponseApiDto<UserUpdateResponse>("200", "User updated with success", userUpdateResponse, LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApiDto<UserDeleteResponse>> deleteUser(@PathVariable UserDeleteRequest userDeleteRequest) {
        UserDeleteResponse response = userService.deleteUser(userDeleteRequest);
        return ResponseEntity.ok(new ResponseApiDto<UserDeleteResponse>("200", "User deleted with success", response, LocalDateTime.now()));
    }
}


