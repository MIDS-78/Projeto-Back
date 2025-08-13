package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserUpdateMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserCaseTest {

    private UserRepository userRepository;
    private UserUpdateMapper updateMapper;
    private UpdateUserCase updateUserCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        updateMapper = mock(UserUpdateMapper.class);
        updateUserCase = new UpdateUserCase(userRepository, updateMapper);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID id = UUID.randomUUID();
        UserUpdateRequest request = new UserUpdateRequest("newName", "new@email.com");

        User existingUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserUpdateResponse expectedResponse = new UserUpdateResponse(id, "newName", "hashedPwd");
        when(updateMapper.toResponse(existingUser)).thenReturn(expectedResponse);

        UserUpdateResponse response = updateUserCase.execute(request, id);

        verify(updateMapper).toEntity(request, existingUser);
        verify(userRepository).save(existingUser);
        assertEquals("newName", response.name());
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        UUID id = UUID.randomUUID();
        UserUpdateRequest request = new UserUpdateRequest("newName", "new@email.com");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUserCase.execute(request, id));
    }
}
