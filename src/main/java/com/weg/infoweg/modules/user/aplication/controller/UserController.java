package com.weg.infoweg.modules.user.aplication.controller;

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


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserGetResponse> getUser(@PathVariable UserGetRequest userGetRequest) {
        userService.getUser(userGetRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateResponse userUpdateResponse = userService.updateUser(request, id);
        return ResponseEntity.ok(new ResponseApiDto<UserUpdateResponse>("200", "User updated with success", userUpdateResponse, LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable UUID id) {
        UserDeleteRequest request = new UserDeleteRequest();
        UserDeleteResponse response = userService.deleteUser(request);
        return ResponseEntity.ok(response);
    }
}


