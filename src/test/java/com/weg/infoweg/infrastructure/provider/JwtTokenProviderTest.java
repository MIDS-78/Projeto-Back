package com.weg.infoweg.infrastructure.provider;
import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsImpl userDetails;

    private final String jwtSecret = "secretoMuitoForte123456789012345678901234567890";
    private final int jwtExpirationInMs = 3600000; // 1 hora

    @BeforeEach
    public void setUp() {
        // Injeta os valores @Value
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", jwtExpirationInMs);

        // Cria UserDetailsImpl com email
        userDetails = new UserDetailsImpl(
                UUID.randomUUID(),
                AccessLevel.STUDENT,
                "senhaTeste",
                "usuarioTeste@email.com",
                "usuario"
        );
    }

    @Test
    public void generateToken_ShouldReturnValidToken() {
        String token = jwtTokenProvider.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String email = jwtTokenProvider.getEmailFromJWT(token);
        assertEquals("usuarioTeste@email.com", email);
    }

    @Test
    public void getEmailFromJWT_WithInvalidToken_ShouldThrowException() {
        // Token com secret inválido
        String invalidToken = com.auth0.jwt.JWT.create()
                .withSubject("usuarioInvalido@email.com")
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256("segredoInvalido"));

        assertThrows(com.auth0.jwt.exceptions.SignatureVerificationException.class, () -> {
            jwtTokenProvider.getEmailFromJWT(invalidToken);
        });
    }

    @Test
    public void valideToken_WithValidToken_ShouldReturnTrue() {
        String token = jwtTokenProvider.generateToken(userDetails);
        assertTrue(jwtTokenProvider.valideToken(token));
    }

    @Test
    public void valideToken_WithInvalidToken_ShouldReturnFalse() {
        String invalidToken = com.auth0.jwt.JWT.create()
                .withSubject("usuarioInvalido@email.com")
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256("segredoInvalido"));
        assertFalse(jwtTokenProvider.valideToken(invalidToken));
    }

    @Test
    public void valideToken_WithExpiredToken_ShouldReturnFalse() throws InterruptedException {
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 1); // 1ms
        String token = jwtTokenProvider.generateToken(userDetails);
        Thread.sleep(10);
        assertFalse(jwtTokenProvider.valideToken(token));
    }
}
