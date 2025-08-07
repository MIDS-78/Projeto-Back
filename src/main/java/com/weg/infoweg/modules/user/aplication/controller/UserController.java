package com.weg.infoweg.modules.user.aplication.controller;

import com.weg.infoweg.modules.user.aplication.dtos.*;
import com.weg.infoweg.modules.user.aplication.port.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> getUser(@PathVariable UserGetRequest userGetRequest) {
        UserGetResponse response = userService.getUser(userGetRequest);
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
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.updateUser(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable Long id) {
        UserDeleteRequest request = new UserDeleteRequest();
        UserDeleteResponse response = userService.deleteUser(request);
        return ResponseEntity.ok(response);
    }
}


