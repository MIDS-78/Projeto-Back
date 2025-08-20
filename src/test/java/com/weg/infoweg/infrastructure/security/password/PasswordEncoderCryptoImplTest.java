package com.weg.infoweg.infrastructure.security.password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordEncoderCryptoImpl - Testes de Unidade")
class PasswordEncoderCryptoImplTest {

    @InjectMocks
    private PasswordEncoderCryptoImpl passwordEncoder;

    @BeforeEach
    void setUp() {
        // Inicializa a classe a ser testada antes de cada teste
        this.passwordEncoder = new PasswordEncoderCryptoImpl();
    }

    @Test
    @DisplayName("Deve criptografar a senha corretamente")
    void shouldEncryptPasswordCorrectly() {
        // Cenário de sucesso
        String password = "mysecretpassword123";
        String encryptedPassword = passwordEncoder.encryptPassword(password);

        // Verificações
        assertNotNull(encryptedPassword); // A senha criptografada não deve ser nula
        assertNotEquals(password, encryptedPassword); // A senha criptografada não deve ser igual à original
        assertTrue(BCrypt.checkpw(password, encryptedPassword)); // A senha original deve corresponder à criptografada
    }

    @Test
    @DisplayName("Deve retornar true para senha correta")
    void shouldReturnTrueForCorrectPassword() {
        // Cenário de senha correta
        String originalPassword = "mysecretpassword123";
        String encryptedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));

        // Verificações
        boolean isMatch = passwordEncoder.checkPassword(originalPassword, encryptedPassword);
        assertTrue(isMatch);
    }

    @Test
    @DisplayName("Deve retornar false para senha incorreta")
    void shouldReturnFalseForIncorrectPassword() {
        // Cenário de senha incorreta
        String originalPassword = "mysecretpassword123";
        String incorrectPassword = "incorrectpassword";
        String encryptedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));

        // Verificações
        boolean isMatch = passwordEncoder.checkPassword(incorrectPassword, encryptedPassword);
        assertFalse(isMatch);
    }
}