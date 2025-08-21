package com.weg.infoweg.modules.token.domain.service;

import com.weg.infoweg.infrastructure.persistence.token.mappers.TokenMapper;
import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.token.domain.ports.TokenRepository;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenMapper tokenMapper;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("testUser");
        testUser.setEmail(new Email("test@example.com"));
        testUser.setPasswordHash("hashedPassword");
        testUser.setAccessLevel(AccessLevel.STUDENT);
    }

    @Test
    void generateAndSaveTokens_ShouldReturnAccessAndRefreshTokens() {
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("accessToken");
        when(jwtTokenProvider.generateRefreshToken(any(UserDetailsImpl.class))).thenReturn("refreshToken");
        when(jwtTokenProvider.getJwtExpirationInMs()).thenReturn(1);
        when(tokenMapper.toEntity(any(JwtTokenDto.class), any(User.class), any(TokenType.class), any(LocalDateTime.class)))
                .thenAnswer(invocation -> new Token());

        List<Token> tokens = tokenService.generateAndSaveTokens(testUser);

        assertEquals(2, tokens.size());
        verify(tokenRepository, times(2)).save(any(Token.class));
        verify(jwtTokenProvider).generateToken(any(UserDetailsImpl.class));
        verify(jwtTokenProvider).generateRefreshToken(any(UserDetailsImpl.class));
    }

    // ---

    @Test
    void revokeAllUserTokens_ShouldCallRepositoryRevokeMethod() {
        // Quando você chama o serviço
        tokenService.revokeAllUserTokens(testUser.getId());

        // Agora, você verifica se o método de revogação em massa do repositório
        // foi chamado exatamente uma vez com o ID do usuário correto.
        verify(tokenRepository, times(1)).revokeAllUserTokens(testUser.getId());

        // As asserções sobre o estado dos objetos token1 e token2 foram removidas,
        // pois a lógica do serviço não os modifica mais em memória.
    }

    // ---

    @Test
    void revokeToken_ShouldSetTokenAsRevoked() {
        Token token = new Token();
        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));

        tokenService.revokeToken(new JwtTokenDto("token123"));

        assertTrue(token.isRevoked());
        verify(tokenRepository).save(token);
    }

    @Test
    void generateToken_ShouldReturnJwtTokenDto() {
        UserDetailsImpl userDetails = new UserDetailsImpl(
                testUser.getId(),
                testUser.getAccessLevel(),
                testUser.getPasswordHash(),
                testUser.getEmail().getAddress(),
                testUser.getUsername()
        );

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("accessToken");
        when(tokenMapper.toEntity(any(JwtTokenDto.class), any(User.class), any(TokenType.class), any(LocalDateTime.class)))
                .thenReturn(new Token());

        JwtTokenDto result = tokenService.generateToken(userDetails);

        assertEquals("accessToken", result.token());
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    void checkValidToken_ShouldReturnTrueWhenTokenIsValid() {
        Token token = new Token();
        token.setRevoked(false);

        when(jwtTokenProvider.valideToken("token123")).thenReturn(true);
        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));

        assertTrue(tokenService.checkValidToken(new JwtTokenDto("token123")));
    }

    @Test
    void checkValidToken_ShouldReturnFalseWhenTokenIsRevokedOrInvalid() {
        Token token = new Token();
        token.setRevoked(true);

        when(jwtTokenProvider.valideToken("token123")).thenReturn(true);
        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));

        assertFalse(tokenService.checkValidToken(new JwtTokenDto("token123")));

        when(jwtTokenProvider.valideToken("tokenInvalid")).thenReturn(false);
        assertFalse(tokenService.checkValidToken(new JwtTokenDto("tokenInvalid")));
    }

    @Test
    void refreshToken_ShouldReturnNewAccessToken() {
        Token oldRefreshToken = new Token();
        oldRefreshToken.setUser(testUser);

        when(tokenRepository.findByToken("oldRefresh")).thenReturn(Optional.of(oldRefreshToken));
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("newAccessToken");
        when(jwtTokenProvider.generateRefreshToken(any(UserDetailsImpl.class))).thenReturn("newRefreshToken");
        when(tokenMapper.toEntity(any(JwtTokenDto.class), any(User.class), any(TokenType.class), any(LocalDateTime.class)))
                .thenReturn(new Token());

        JwtTokenDto newToken = tokenService.refreshToken(new JwtTokenDto("oldRefresh"));

        assertEquals("newAccessToken", newToken.token());
        assertTrue(oldRefreshToken.isRevoked());
        verify(tokenRepository, times(2)).save(any(Token.class));
    }
}