package com.weg.infoweg.modules.token.domain.service;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.token.domain.ports.TokenRepository;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
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

    @InjectMocks
    private TokenServiceImpl tokenService;

    private User testUser;
    private UserDetailsImpl testUserDetails;

    @BeforeEach
    void setUp() {
        testUser = new User(UUID.randomUUID(), "testUser", new Email("teste@weg.net"), "password", "4324242342242", AccessLevel.STUDENT);
        testUserDetails = new UserDetailsImpl(testUser.getId(), testUser.getAccessLevel(), testUser.getPasswordHash(), testUser.getEmail().getAddress(), testUser.getUsername());
    }

    @Test
    void shouldGenerateAndSaveTokensSuccessfully() {
        // Mock the provider's behavior
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("fakeAccessToken");
        when(jwtTokenProvider.generateRefreshToken(any(UserDetailsImpl.class))).thenReturn("fakeRefreshToken");

        // Execute the method to be tested
        List<Token> tokens = tokenService.generateAndSaveTokens(testUser);

        // Verify repository interaction
        verify(tokenRepository, times(1)).findAllValidTokensByUserId(testUser.getId());
        verify(tokenRepository, times(2)).save(any(Token.class));

        // Verify returned tokens
        assertNotNull(tokens);
        assertEquals(2, tokens.size());
    }

    @Test
    void shouldRevokeAllUserTokensSuccessfully() {
        // Mock existing tokens for a user
        List<Token> activeTokens = List.of(new Token(), new Token());
        when(tokenRepository.findAllValidTokensByUserId(testUser.getId())).thenReturn(activeTokens);

        // Execute the method to be tested
        tokenService.revokeAllUserTokens(testUser.getId());

        // Verify repository interaction
        verify(tokenRepository, times(1)).saveAll(anyList());

        // Verify tokens are marked as revoked
        assertTrue(activeTokens.get(0).isRevoked());
        assertTrue(activeTokens.get(1).isRevoked());
    }

    @Test
    void shouldNotRevokeTokensIfUserHasNone() {
        // Mock an empty list of tokens
        when(tokenRepository.findAllValidTokensByUserId(testUser.getId())).thenReturn(List.of());

        // Execute the method to be tested
        tokenService.revokeAllUserTokens(testUser.getId());

        // Verify that saveAll is not called
        verify(tokenRepository, never()).saveAll(anyList());
    }

    @Test
    void shouldRevokeSpecificTokenSuccessfully() {
        // Mock an existing token in the database
        String tokenString = "validToken";
        Token tokenToRevoke = new Token();
        tokenToRevoke.setToken(tokenString);
        when(tokenRepository.findByToken(tokenString)).thenReturn(Optional.of(tokenToRevoke));

        // Execute the method to be tested
        tokenService.revokeToken(new JwtTokenDto(tokenString));

        // Verify token is saved with revoked status
        assertTrue(tokenToRevoke.isRevoked());
        verify(tokenRepository, times(1)).save(tokenToRevoke);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        // Mock the provider's behavior
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("newAccessToken");

        // Execute the method to be tested
        JwtTokenDto tokenDto = tokenService.generateToken(testUser);

        // Verify the returned token
        assertNotNull(tokenDto);
        assertEquals("newAccessToken", tokenDto.token());
    }

    @Test
    void shouldCheckValidTokenSuccessfully() {
        // Mock a valid token in the database and a valid JWT
        String tokenString = "validJwt";
        Token validToken = new Token();
        validToken.setToken(tokenString);
        validToken.setRevoked(false);

        when(jwtTokenProvider.valideToken(tokenString)).thenReturn(true);
        when(tokenRepository.findByToken(tokenString)).thenReturn(Optional.of(validToken));

        // Execute the method to be tested and assert true
        assertTrue(tokenService.checkValidToken(new JwtTokenDto(tokenString)));
    }

    @Test
    void shouldFailCheckValidTokenWhenJwtIsInvalid() {
        // Mock an invalid JWT
        String tokenString = "invalidJwt";
        when(jwtTokenProvider.valideToken(tokenString)).thenReturn(false);

        // Execute the method and assert false
        assertFalse(tokenService.checkValidToken(new JwtTokenDto(tokenString)));
    }

    @Test
    void shouldFailCheckValidTokenWhenTokenIsRevoked() {
        // Mock a valid JWT but a revoked token in the database
        String tokenString = "revokedToken";
        Token revokedToken = new Token();
        revokedToken.setRevoked(true);

        when(jwtTokenProvider.valideToken(tokenString)).thenReturn(true);
        when(tokenRepository.findByToken(tokenString)).thenReturn(Optional.of(revokedToken));

        // Execute the method and assert false
        assertFalse(tokenService.checkValidToken(new JwtTokenDto(tokenString)));
    }

    @Test
    void shouldRefreshTokenSuccessfully() {
        // Mock old refresh token entity
        String oldRefreshTokenString = "oldRefreshToken";

        // Cria uma nova instância de Token e define o usuário mock criado no setUp
        Token oldRefreshTokenEntity = new Token();
        oldRefreshTokenEntity.setToken(oldRefreshTokenString);
        oldRefreshTokenEntity.setTokenType(TokenType.REFRESH);
        oldRefreshTokenEntity.setUser(testUser); // <--- AQUI ESTÁ A CORREÇÃO

        when(tokenRepository.findByToken(oldRefreshTokenString)).thenReturn(Optional.of(oldRefreshTokenEntity));
        when(jwtTokenProvider.generateToken(any(UserDetailsImpl.class))).thenReturn("newAccessToken");
        when(jwtTokenProvider.generateRefreshToken(any(UserDetailsImpl.class))).thenReturn("newRefreshToken");

        // Execute the method
        JwtTokenDto newTokenDto = tokenService.refreshToken(new JwtTokenDto(oldRefreshTokenString));

        // Verify the old token is revoked
        assertTrue(oldRefreshTokenEntity.isRevoked());

        // Verify repository interactions
        verify(tokenRepository, times(2)).save(any(Token.class));
        verify(tokenRepository).save(oldRefreshTokenEntity);

        // Verify the returned token
        assertEquals("newAccessToken", newTokenDto.token());
    }

    @Test
    void shouldThrowExceptionWhenRefreshingInvalidToken() {
        // Mock that the token is not found
        String invalidToken = "invalidRefreshToken";
        when(tokenRepository.findByToken(invalidToken)).thenReturn(Optional.empty());

        // Assert that the expected exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tokenService.refreshToken(new JwtTokenDto(invalidToken)));

        assertEquals("Refresh token inválido ou não encontrado", exception.getMessage());
        verify(tokenRepository, never()).save(any());
    }
}