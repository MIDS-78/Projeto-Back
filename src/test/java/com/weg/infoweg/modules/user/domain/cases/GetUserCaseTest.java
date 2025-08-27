package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserGetMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserCaseTest {

    private UserRepository userRepository;
    private GetUserCase getUserCase;
    private UserGetMapper userGetMapper;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userGetMapper = mock(UserGetMapper.class);

        getUserCase = new GetUserCase(userRepository, userGetMapper);
    }

    @Test
    void shouldReturnUserSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserGetRequest request = new UserGetRequest(id);

        User user = new User("john",
                new Email("john@weg.com", s -> true),
                "password1234",
                "47999999999",
                AccessLevel.STUDENT);
        user.setId(id);

        UserGetResponse expectedResponse = new UserGetResponse(id, "john@weg.com", "john", "47999999999", AccessLevel.STUDENT);

        // Tell the mocks what to do when their methods are called
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        // Add this line to mock the behavior of the mapper
        when(userGetMapper.toResponse(user)).thenReturn(expectedResponse);

        // Act
        UserGetResponse response = getUserCase.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.name(), response.name());
        assertEquals(expectedResponse.email(), response.email());
        // Verify that the repository and mapper were called correctly
        verify(userRepository, times(1)).findById(id);
        verify(userGetMapper, times(1)).toResponse(user);
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        UUID id = UUID.randomUUID();
        UserGetRequest request = new UserGetRequest(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> getUserCase.execute(request));

        verify(userRepository, times(1)).findById(id);
        verifyNoInteractions(userGetMapper);
    }
}