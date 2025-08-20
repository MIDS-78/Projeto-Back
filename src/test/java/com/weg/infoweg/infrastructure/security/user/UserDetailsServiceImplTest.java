package com.weg.infoweg.infrastructure.security.user;

import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        // Criando um usuário de teste
        UUID userId = UUID.randomUUID();
        User mockUser = new User(
                userId,
                "testeUsuario",
                new Email("teste@exemplo.com"),
                "senhaHashed",
                "+5511999999999",
                AccessLevel.STUDENT
        );

        // Configurando o mock para aceitar qualquer Email com o mesmo endereço
        when(userRepository.findByEmail(argThat(email -> email.getAddress().equals("teste@exemplo.com"))))
                .thenReturn(Optional.of(mockUser));

        // Executando o método
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("teste@exemplo.com");

        // Verificando resultados
        assertNotNull(userDetails);
        assertEquals(mockUser.getEmail().getAddress(), userDetails.getEmail());
        assertEquals(mockUser.getPasswordHash(), userDetails.getPassword());

        // Verificando que o método do repositório foi chamado corretamente
        verify(userRepository, times(1))
                .findByEmail(argThat(email -> email.getAddress().equals("teste@exemplo.com")));
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        String nonexistentEmailStr = "naoexiste@exemplo.com";

        // Usando any(Email.class) para aceitar qualquer Email que tenha esse valor
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        // Verificando que lança exceção
        assertThrows(UserNotFoundException.class, () ->
                userDetailsService.loadUserByUsername(nonexistentEmailStr)
        );

        // Você pode verificar que foi chamado com qualquer Email, ou com um matcher mais específico
        verify(userRepository, times(1)).findByEmail(any(Email.class));
    }
}
