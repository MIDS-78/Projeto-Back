package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.token.domain.exceptions.TokenInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RefleshUserCaseTest {

    private TokenService tokenService;
    private RefleshUserCase refleshUserCase;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        refleshUserCase = new RefleshUserCase(tokenService);
    }

    @Test
    void execute_shouldReturnNewToken_whenOldTokenIsValid() {
        UUID userId = UUID.randomUUID();
        JwtTokenDto oldToken = new JwtTokenDto("oldToken");
        JwtTokenDto newToken = new JwtTokenDto("newToken");

        when(tokenService.checkValidToken(oldToken)).thenReturn(true);
        when(tokenService.refreshToken(oldToken)).thenReturn(newToken);

        JwtTokenDto result = refleshUserCase.execute(userId, oldToken);

        assertEquals(newToken, result);
        verify(tokenService, times(1)).checkValidToken(oldToken);
        verify(tokenService, times(1)).refreshToken(oldToken);
    }

    @Test
    void execute_shouldThrowException_whenOldTokenIsInvalid() {
        UUID userId = UUID.randomUUID();
        JwtTokenDto oldToken = new JwtTokenDto("invalidToken");

        when(tokenService.checkValidToken(oldToken)).thenReturn(false);

        assertThrows(TokenInvalidException.class,
                () -> refleshUserCase.execute(userId, oldToken));

        verify(tokenService, times(1)).checkValidToken(oldToken);
        verify(tokenService, never()).refreshToken(any());
    }
}
