package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.Informative;
import com.weg.infoweg.modules.informative.aplication.dtos.InformativeDeleteRequest;
import com.weg.infoweg.modules.informative.domain.exception.InformativeNotFoundException;
import com.weg.infoweg.modules.informative.domain.exception.UserWithoutPermissionInformativeException;
import com.weg.infoweg.modules.informative.ports.InformativeRepository;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteInformativeCaseTest {

    private InformativeRepository informativeRepository;
    private UserRepository userRepository;
    private DeleteInformativeCase deleteInformativeCase;

    @BeforeEach
    void setUp() {
        informativeRepository = mock(InformativeRepository.class);
        userRepository = mock(UserRepository.class);
        deleteInformativeCase = new DeleteInformativeCase(informativeRepository, userRepository);
    }

    @Test
    void execute_shouldDelete_whenUserHasPermissionAndInformativeExists() {
        UUID userId = UUID.randomUUID();
        UUID informativeId = UUID.randomUUID();

        // Usuário com permissão
        User user = mock(User.class);
        when(user.canDeleteInformative()).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Informativo existente
        Informative informative = new Informative();
        when(informativeRepository.findById(informativeId)).thenReturn(Optional.of(informative));

        InformativeDeleteRequest request = new InformativeDeleteRequest(informativeId);

        // Executa sem lançar exceção
        assertDoesNotThrow(() -> deleteInformativeCase.execute(request, userId));

        // Verifica se foi realmente deletado
        verify(informativeRepository).deleteById(informativeId);
    }

    @Test
    void execute_shouldThrowException_whenUserHasNoPermission() {
        UUID userId = UUID.randomUUID();
        UUID informativeId = UUID.randomUUID();

        // Usuário sem permissão
        User user = mock(User.class);
        when(user.canDeleteInformative()).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        InformativeDeleteRequest request = new InformativeDeleteRequest(informativeId);

        UserWithoutPermissionInformativeException exception = assertThrows(
                UserWithoutPermissionInformativeException.class,
                () -> deleteInformativeCase.execute(request, userId)
        );

        assertEquals("User is not authorized to delete informatives", exception.getMessage());

        verify(informativeRepository, never()).deleteById(any());
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UUID informativeId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        InformativeDeleteRequest request = new InformativeDeleteRequest(informativeId);

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> deleteInformativeCase.execute(request, userId)
        );

        assertEquals(new UserNotFoundException("User not found").getMessage(), exception.getMessage());

        verify(informativeRepository, never()).deleteById(any());
    }

    @Test
    void execute_shouldThrowException_whenInformativeDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID informativeId = UUID.randomUUID();

        // Usuário com permissão
        User user = mock(User.class);
        when(user.canDeleteInformative()).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Informativo inexistente
        when(informativeRepository.findById(informativeId)).thenReturn(Optional.empty());

        InformativeDeleteRequest request = new InformativeDeleteRequest(informativeId);

        InformativeNotFoundException exception = assertThrows(
                InformativeNotFoundException.class,
                () -> deleteInformativeCase.execute(request, userId)
        );

        assertEquals("Record not found by ID: " + informativeId, exception.getMessage());

        verify(informativeRepository, never()).deleteById(any());
    }
}
