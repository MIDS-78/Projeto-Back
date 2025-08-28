package com.weg.infoweg.modules.user.application.controller;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.user.application.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.application.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.application.dtos.UserDeleteRequest;
import com.weg.infoweg.modules.user.application.dtos.UserGetRequest;
import com.weg.infoweg.modules.user.application.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.application.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.application.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.application.port.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}") //
    public ResponseEntity<ResponseApiDto<UserGetResponse>> getUser(@PathVariable UUID id) {
        UserGetResponse userGetResponse = userService.getUser(new UserGetRequest(id));
        return ResponseEntity.ok(ResponseApiDto.success("User get with success", userGetResponse));
    }

    @PostMapping
    public ResponseEntity<ResponseApiDto<UserCreateResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {
        UUID id = userAuthenticationService.getIdUserAuthentication();
        UserCreateResponse response = userService.createUser(request, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApiDto.success("User created with success", response));
    }

    @PutMapping("/{id}") //
    public ResponseEntity<ResponseApiDto<UserUpdateResponse>> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateResponse userUpdateResponse = userService.updateUser(request, id);
        return ResponseEntity.ok(ResponseApiDto.success("User updated with success", userUpdateResponse));
    }

    @DeleteMapping("/{id}") //
    public ResponseEntity<ResponseApiDto<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(new UserDeleteRequest(id));
        return ResponseEntity.ok(ResponseApiDto.success("User deleted with success"));
    }
}