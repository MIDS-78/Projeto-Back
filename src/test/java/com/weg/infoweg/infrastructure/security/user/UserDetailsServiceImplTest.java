package com.weg.infoweg.infrastructure.security.user;

import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {


    // Simula o repositório, não chamando o código real do DB
    @Mock
    private UserRepository userRepository;

    // Injeta os mocks na classe que estamos testando
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    // Objeto de usuário para usar nos testes
    private User user;

    @BeforeEach
    void setUp() {

        // Inicializa o objeto de usuário antes de cada teste
        user = new User("testuser", new Email("jaafadsa@weg.net"), "password123", "3423424234234", AccessLevel.STUDENT);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        // Define o comportamento do repositório
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        // Chama o método que queremos testar
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Verifica se o resultado está correto
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUserName("testuser");
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Define o comportamento do repositório para retornar vazio
        when(userRepository.findByUserName("nonexistent")).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        assertThrows(UserNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });
        verify(userRepository, times(1)).findByUserName("nonexistent");
    }
}
