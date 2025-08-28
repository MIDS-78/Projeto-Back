package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.modules.user.application.dtos.UserDeleteRequest;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteUserCaseTest {

    private UserRepository userRepository;
    private DeleteUserCase deleteUserCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        deleteUserCase = new DeleteUserCase(userRepository);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();
        UserDeleteRequest request = new UserDeleteRequest(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        deleteUserCase.execute(request);

        verify(userRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        UUID id = UUID.randomUUID();
        UserDeleteRequest request = new UserDeleteRequest(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> deleteUserCase.execute(request));
    }
}
