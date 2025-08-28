package com.weg.infoweg.modules.auth.domain.service;

import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.application.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.application.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.domain.cases.LoginUserCase;
import com.weg.infoweg.modules.auth.domain.cases.LogoutUserCase;
import com.weg.infoweg.modules.auth.domain.cases.RefreshUserCase;
import com.weg.infoweg.modules.auth.domain.cases.RegisterUserCase;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl - Testes Unitários")
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private LoginUserCase loginUserCase;

    @Mock
    private RegisterUserCase registerUserCase;

    @Mock
    private LogoutUserCase logoutUserCase;

    @Mock
    private RefreshUserCase refreshUserCase;

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar o DTO de resposta")
    void testLogin_Success() {
        // Cenário (Arrange)
        UserLoginRequest loginRequest = new UserLoginRequest("test@email.com", "password123");
        JwtTokenDto jwtTokenDto = new JwtTokenDto("mock_token_string");
        when(loginUserCase.execute(loginRequest)).thenReturn(jwtTokenDto);

        // Ação (Act)
        UserLoginResponse loginResponse = authService.login(loginRequest);

        // Verificação (Assert)
        assertNotNull(loginResponse);
        assertEquals(jwtTokenDto, loginResponse.jwtTokenDto());
        verify(loginUserCase, times(1)).execute(loginRequest);
    }

    @Test
    @DisplayName("Deve registrar um novo usuário e retornar o DTO de resposta")
    void testRegister_Success() {
        // Cenário (Arrange)
        UserRegisterRequest registerRequest = new UserRegisterRequest("testuser", "test@email.com", "password123", "999999999");
        UserRegisterResponse registerResponse = new UserRegisterResponse("User registered successfully");
        when(registerUserCase.execute(registerRequest)).thenReturn(registerResponse);

        // Ação (Act)
        UserRegisterResponse result = authService.register(registerRequest);

        // Verificação (Assert)
        assertNotNull(result);
        assertEquals(registerResponse, result);
        assertEquals("User registered successfully", result.message());
        verify(registerUserCase, times(1)).execute(registerRequest);
    }

    @Test
    @DisplayName("Deve realizar logout sem erros")
    void testLogout_Success() {
        // Cenário (Arrange)
        UUID userId = UUID.randomUUID();
        JwtTokenDto jwtTokenDto = new JwtTokenDto("mock_token_string");
        doNothing().when(logoutUserCase).execute(userId, jwtTokenDto);

        // Ação (Act)
        authService.logout(userId, jwtTokenDto);

        // Verificação (Assert)
        verify(logoutUserCase, times(1)).execute(userId, jwtTokenDto);
    }

    @Test
    @DisplayName("Deve atualizar o token com sucesso")
    void testRefresh_Success() {
        // Cenário (Arrange)
        UUID userId = UUID.randomUUID();
        JwtTokenDto oldTokenDto = new JwtTokenDto("old-token-string");
        JwtTokenDto newTokenDto = new JwtTokenDto("new-token-string");
        when(refreshUserCase.execute(userId, oldTokenDto)).thenReturn(newTokenDto);

        // Ação (Act)
        JwtTokenDto result = authService.refresh(userId, oldTokenDto);

        // Verificação (Assert)
        assertNotNull(result);
        assertEquals(newTokenDto, result);
        verify(refreshUserCase, times(1)).execute(userId, oldTokenDto);
    }
}