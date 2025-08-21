package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutUserCaseTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LogoutUserCase logoutUserCase;

    private User testUser;
    private UUID testUserId;
    private JwtTokenDto testJwtTokenDto;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testJwtTokenDto = new JwtTokenDto("token123");
        testUser = new User();
        testUser.setId(testUserId);
    }

    @Test
    void execute_shouldRevokeAllUserTokens_whenUserExists() {
        // Arrange
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        // Act
        logoutUserCase.execute(testUserId, testJwtTokenDto);

        // Assert
        // Apenas verifique o método que é realmente chamado no use case de produção.
        verify(tokenService, times(1)).revokeAllUserTokens(testUserId);

        // Verifique se o outro método não foi chamado, por clareza e segurança.
        verify(tokenService, never()).revokeToken(any());
    }

    @Test
    void execute_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class,
                () -> logoutUserCase.execute(testUserId, testJwtTokenDto));

        // Verifique que nenhum método de revogação foi chamado
        verify(tokenService, never()).revokeToken(any());
        verify(tokenService, never()).revokeAllUserTokens(any());
    }
}