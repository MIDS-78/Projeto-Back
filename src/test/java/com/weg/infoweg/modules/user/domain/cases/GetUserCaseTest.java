package com.weg.infoweg.modules.user.domain.cases;

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

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        getUserCase = new GetUserCase(userRepository);
    }

    @Test
    void shouldReturnUserSuccessfully() {
        UUID id = UUID.randomUUID();
        UserGetRequest request = new UserGetRequest(id);

        User user = new User("john",
                new Email("john@weg.com", s -> true),
                "password1234",
                "47999999999",
                AccessLevel.STUDENT);
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserGetResponse response = getUserCase.execute(request);

        assertEquals("john", response.email());
        assertEquals("john@weg.com", response.email());
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        UUID id = UUID.randomUUID();
        UserGetRequest request = new UserGetRequest(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> getUserCase.execute(request));
    }
}
