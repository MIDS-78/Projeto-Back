package com.weg.infoweg.modules.user.domain.valueobjects;

import com.weg.infoweg.modules.user.domain.exceptions.InvalidEmailException;
import com.weg.infoweg.modules.user.domain.ports.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailTest {

    @Mock
    private EmailValidator emailValidator;

    @BeforeEach
    void setup() {  }

    @Test
    @DisplayName("Should create an email successfully when the email is valid")
    void shouldCreateEmailSuccessfullyWhenEmailIsValid() {
        String validEmail = "testuser@weg.net";
        when(emailValidator.isValid(validEmail)).thenReturn(true);

        assertDoesNotThrow(() -> new Email(validEmail, emailValidator));
    }

    @Test
    @DisplayName("Should throw an exception when the email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        String invalidEmail = "testuser@gmail.com";
        when(emailValidator.isValid(invalidEmail)).thenReturn(false);

        assertThrows(InvalidEmailException.class, () -> new Email(invalidEmail, emailValidator));
    }
}