package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LogoutUserCaseTest {

    private TokenService tokenService;
    private UserRepository userRepository;
    private LogoutUserCase logoutUserCase;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        userRepository = mock(UserRepository.class);
        logoutUserCase = new LogoutUserCase(tokenService, userRepository);
    }

    @Test
    void execute_shouldRevokeTokens_whenUserExists() {
        UUID userId = UUID.randomUUID();
        JwtTokenDto jwtTokenDto = new JwtTokenDto("token123");
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        logoutUserCase.execute(userId, jwtTokenDto);

        verify(tokenService, times(1)).revokeToken(jwtTokenDto);
        verify(tokenService, times(1)).revokeAllUserTokens(userId);
    }

    @Test
    void execute_shouldThrowException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        JwtTokenDto jwtTokenDto = new JwtTokenDto("token123");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> logoutUserCase.execute(userId, jwtTokenDto));

        verify(tokenService, never()).revokeToken(any());
        verify(tokenService, never()).revokeAllUserTokens(any());
    }
}
