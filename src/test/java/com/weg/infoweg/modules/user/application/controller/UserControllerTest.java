package com.weg.infoweg.modules.user.application.controller;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.user.application.dtos.*;
import com.weg.infoweg.modules.user.application.port.UserService;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @InjectMocks
    private UserController userController;

    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
    }

    @Test
    void getUser_ShouldReturnUserGetResponse_WhenSuccessful() {
        // Arrange
        UserGetRequest request = new UserGetRequest(userId);
        UserGetResponse response = new UserGetResponse(userId, "testuser", "test@example.com", "123456789", AccessLevel.STUDENT);
        when(userService.getUser(any(UserGetRequest.class))).thenReturn(response);

        // Act
        ResponseEntity<ResponseApiDto<UserGetResponse>> responseEntity = userController.getUser(request.id());

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(response, responseEntity.getBody().data());
    }

    @Test
    void createUser_ShouldReturnUserCreateResponse_WhenSuccessful() {
        // Arrange
        UserCreateRequest request = new UserCreateRequest(
                "testuser",
                "test@example.com",
                "password123",
                "123456789",
                AccessLevel.STUDENT
        );

        UserCreateResponse userResponse = new UserCreateResponse(
                userId,
                "testuser",
                "test@example.com",
                "123456789",
                AccessLevel.STUDENT
        );

        when(userAuthenticationService.getIdUserAuthentication()).thenReturn(userId);
        when(userService.createUser(any(UserCreateRequest.class), any(UUID.class)))
                .thenReturn(userResponse);

        // Act
        ResponseEntity<ResponseApiDto<UserCreateResponse>> responseEntity = userController.createUser(request);

        // Assert
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCode().value());
        assertEquals("testuser", responseEntity.getBody().data().username());
    }


    @Test
    void updateUser_ShouldReturnUserUpdateResponse_WhenSuccessful() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest("newuser", "new@example.com");
        UserUpdateResponse response = new UserUpdateResponse(userId, "newuser", "new@example.com");
        when(userService.updateUser(any(UserUpdateRequest.class), any(UUID.class))).thenReturn(response);

        // Act
        ResponseEntity<ResponseApiDto<UserUpdateResponse>> responseEntity = userController.updateUser(userId, request);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(response, responseEntity.getBody().data());
    }

    @Test
    void deleteUser_ShouldReturnUserDeleteResponse_WhenSuccessful() {
        UserDeleteRequest request = new UserDeleteRequest(userId);

        ResponseEntity<ResponseApiDto<Void>> responseEntity = userController.deleteUser(request.id());

        assertEquals(200, responseEntity.getStatusCode().value());
    }
}