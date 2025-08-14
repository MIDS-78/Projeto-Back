package com.weg.infoweg.infrastructure.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private UserDetails userDetails;

    private final String jwtSecret = "secretoMuitoForte123456789012345678901234567890";
    private final int jwtExpirationInMs = 3600000; // 1 hora

    @BeforeEach
    public void setUp() {
        // Usa ReflectionTestUtils para injetar os valores @Value da classe
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", jwtExpirationInMs);

        // Cria um mock de UserDetails para ser usado nos testes
        userDetails = new User("usuarioTeste", "senhaTeste", Collections.emptyList());
    }

    @Test
    public void generateToken_ShouldReturnValidToken() {
        // Gera o token
        String token = jwtTokenProvider.generateToken(userDetails);

        // Verifica se o token não é nulo ou vazio
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Decodifica e verifica o token
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        String subject = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();

        // Verifica se o subject (nome de usuário) do token é o mesmo do usuário
        assertEquals("usuarioTeste", subject);
    }

    @Test
    public void getUsernameFromJWT_ShouldReturnCorrectUsername() {
        // Gera um token válido
        String token = jwtTokenProvider.generateToken(userDetails);

        // Extrai o nome de usuário do token
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        // Verifica se o nome de usuário extraído é o esperado
        assertEquals("usuarioTeste", username);
    }

    @Test
    public void getUsernameFromJWT_WithInvalidToken_ShouldThrowException() {
        // Cria um token inválido (com um secret diferente)
        Algorithm invalidAlgorithm = Algorithm.HMAC256("segredoInvalido");
        String invalidToken = JWT.create()
                .withSubject("usuarioInvalido")
                .sign(invalidAlgorithm);

        // Garante que o método lance uma exceção para um token inválido
        assertThrows(com.auth0.jwt.exceptions.SignatureVerificationException.class, () -> {
            jwtTokenProvider.getUsernameFromJWT(invalidToken);
        });
    }

    @Test
    public void valideToken_WithValidToken_ShouldReturnTrue() {
        // Gera um token válido
        String token = jwtTokenProvider.generateToken(userDetails);

        // Verifica se a validação retorna 'true'
        assertTrue(jwtTokenProvider.valideToken(token));
    }

    @Test
    public void valideToken_WithInvalidToken_ShouldReturnFalse() {
        // Cria um token inválido (secret diferente)
        Algorithm invalidAlgorithm = Algorithm.HMAC256("segredoInvalido");
        String invalidToken = JWT.create()
                .withSubject("usuarioInvalido")
                .sign(invalidAlgorithm);

        // Verifica se a validação retorna 'false' para um token inválido
        assertFalse(jwtTokenProvider.valideToken(invalidToken));
    }

    @Test
    public void valideToken_WithExpiredToken_ShouldReturnFalse() throws InterruptedException {
        // Define um tempo de expiração muito curto (1 milissegundo) para o teste
        int shortExpiration = 1;
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", shortExpiration);

        // Gera um token que expira quase instantaneamente
        String token = jwtTokenProvider.generateToken(userDetails);

        // Aguarda um tempo maior que a expiração para garantir que o token expire
        Thread.sleep(10);

        // Verifica se a validação falha para um token expirado
        assertFalse(jwtTokenProvider.valideToken(token));
    }
}