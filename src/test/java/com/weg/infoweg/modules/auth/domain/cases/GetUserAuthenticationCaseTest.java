package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserGetMapper;
import com.weg.infoweg.modules.auth.domain.cases.GetUserAuthenticationCase;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserAuthenticationCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserGetMapper userGetMapper;

    @InjectMocks
    private GetUserAuthenticationCase getUserAuthenticationCase;

    private UUID userId;
    private User mockUser;
    private UserGetResponse mockUserResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        mockUser = new User();
        mockUserResponse = new UserGetResponse(userId, "test.user@weg.net", "Test User", "999999999999999", AccessLevel.STUDENT);
    }

    @Test
    @DisplayName("Should return a UserGetResponse when a user is found")
    void shouldReturnUserGetResponseWhenUserIsFound() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userGetMapper.toResponse(mockUser)).thenReturn(mockUserResponse);

        // Act
        UserGetResponse result = getUserAuthenticationCase.execute(userId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(mockUserResponse.id(), result.id(), "The returned user ID should match the mock ID");
        assertEquals(mockUserResponse.email(), result.email(), "The returned email should match the mock email");
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when a user is not found")
    void shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> getUserAuthenticationCase.execute(userId),
                "Executing with a non-existent UUID should throw a UserNotFoundException");
    }
}