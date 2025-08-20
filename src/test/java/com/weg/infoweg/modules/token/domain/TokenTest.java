package com.weg.infoweg.modules.token.domain;

import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    private User testUser;
    private Token token;

    @BeforeEach
    void setUp() {
        // Criando um usuário mock simples
        testUser = new User(UUID.randomUUID(), "testUser", new Email("teste@weg.net"), "password", "999999999", AccessLevel.STUDENT);

        // Criando um token usando o construtor completo
        token = new Token(
                UUID.randomUUID(),
                testUser,
                "fakeTokenValue",
                TokenType.ACCESS,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                false
        );
    }

    @Test
    void testGetters() {
        assertNotNull(token.getId());
        assertEquals(testUser, token.getUser());
        assertEquals("fakeTokenValue", token.getToken());
        assertEquals(TokenType.ACCESS, token.getTokenType());
        assertFalse(token.isRevoked());
        assertNotNull(token.getCreatedAt());
        assertNotNull(token.getUpdatedAt());
        assertNotNull(token.getExpiresAt());
    }

    @Test
    void testSetters() {
        UUID newId = UUID.randomUUID();
        token.setId(newId);
        assertEquals(newId, token.getId());

        User newUser = new User(UUID.randomUUID(), "newUser", new Email("novo@weg.net"), "1234", "111111", AccessLevel.STUDENT);
        token.setUser(newUser);
        assertEquals(newUser, token.getUser());

        token.setToken("newTokenValue");
        assertEquals("newTokenValue", token.getToken());

        token.setTokenType(TokenType.REFRESH);
        assertEquals(TokenType.REFRESH, token.getTokenType());

        LocalDateTime newCreated = LocalDateTime.now().minusDays(1);
        token.setCreatedAt(newCreated);
        assertEquals(newCreated, token.getCreatedAt());

        LocalDateTime newUpdated = LocalDateTime.now();
        token.setUpdatedAt(newUpdated);
        assertEquals(newUpdated, token.getUpdatedAt());

        LocalDateTime newExpires = LocalDateTime.now().plusDays(1);
        token.setExpiresAt(newExpires);
        assertEquals(newExpires, token.getExpiresAt());

        token.setRevoked(true);
        assertTrue(token.isRevoked());
    }

    @Test
    void testEmptyConstructor() {
        Token emptyToken = new Token();
        assertNotNull(emptyToken);
    }

    @Test
    void testConstructorWithThreeArguments() {
        Token simpleToken = new Token("token123", TokenType.ACCESS, testUser, LocalDateTime.now().plusHours(1));
        assertEquals("token123", simpleToken.getToken());
        assertEquals(TokenType.ACCESS, simpleToken.getTokenType());
        assertEquals(testUser, simpleToken.getUser());
        assertFalse(simpleToken.isRevoked());
        assertNotNull(simpleToken.getCreatedAt());
        assertNotNull(simpleToken.getUpdatedAt());
    }
}
