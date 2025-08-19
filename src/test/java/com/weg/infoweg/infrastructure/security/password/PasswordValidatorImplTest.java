package com.weg.infoweg.infrastructure.security.password;

import com.weg.infoweg.modules.auth.domain.exceptions.PasswordValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PasswordValidatorImpl - Testes de Unidade")
class PasswordValidatorImplTest {

    private PasswordValidatorImpl passwordValidator;

    @BeforeEach
    void setUp() {
        // Inicializa a classe a ser testada antes de cada teste.
        this.passwordValidator = new PasswordValidatorImpl();
    }

    @Test
    @DisplayName("Deve retornar true para uma senha válida")
    void shouldReturnTrueForValidPassword() {
        // Uma senha que atende a todos os critérios
        String validPassword = "Password123!";
        assertTrue(passwordValidator.isValid(validPassword));
    }

    //---------------------------------------------------------

    @Test
    @DisplayName("Deve lançar exceção se a senha for muito curta")
    void shouldThrowExceptionIfPasswordIsTooShort() {
        String shortPassword = "short";
        PasswordValidException thrown = assertThrows(
                PasswordValidException.class,
                () -> passwordValidator.isValid(shortPassword)
        );
        assertEquals("Password must be at least 8 characters long", thrown.getMessage());
    }

    //---------------------------------------------------------

    @Test
    @DisplayName("Deve lançar exceção se a senha não contiver caractere especial")
    void shouldThrowExceptionIfPasswordHasNoSpecialCharacter() {
        String noSpecialCharPassword = "Password123";
        PasswordValidException thrown = assertThrows(
                PasswordValidException.class,
                () -> passwordValidator.isValid(noSpecialCharPassword)
        );
        assertEquals("Password must contain at least one special character", thrown.getMessage());
    }

    //---------------------------------------------------------

    @Test
    @DisplayName("Deve lançar exceção se a senha não contiver número")
    void shouldThrowExceptionIfPasswordHasNoNumber() {
        String noNumberPassword = "Password!!";
        PasswordValidException thrown = assertThrows(
                PasswordValidException.class,
                () -> passwordValidator.isValid(noNumberPassword)
        );
        assertEquals("Password must contain at least one digit", thrown.getMessage());
    }

    //---------------------------------------------------------

    @Test
    @DisplayName("Deve lançar exceção se a senha não contiver letra")
    void shouldThrowExceptionIfPasswordHasNoLetter() {
        String noLetterPassword = "123456789!@#";
        PasswordValidException thrown = assertThrows(
                PasswordValidException.class,
                () -> passwordValidator.isValid(noLetterPassword)
        );
        assertEquals("Password must contain at least one letter", thrown.getMessage());
    }

    //---------------------------------------------------------

    @Test
    @DisplayName("Deve lançar exceção se a senha for nula")
    void shouldThrowExceptionIfPasswordIsNull() {
        String nullPassword = null;
        PasswordValidException thrown = assertThrows(
                PasswordValidException.class,
                () -> passwordValidator.isValid(nullPassword)
        );
        assertEquals("Password must be at least 8 characters long", thrown.getMessage());
    }
}