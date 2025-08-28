package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserCreateMapper;
import com.weg.infoweg.modules.user.application.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.application.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserCaseTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserCreateMapper userCreateMapper;
    private CreateUserCase createUserCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userCreateMapper = mock(UserCreateMapper.class);
        createUserCase = new CreateUserCase(userRepository, passwordEncoder, userCreateMapper);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserCreateRequest req = new UserCreateRequest("john", "john@weg.com", "password", "47999999999", null);
        UUID id = UUID.randomUUID();

        when(passwordEncoder.encode("password")).thenReturn("hash");
        User userEntity = new User();
        when(userCreateMapper.toEntity(req, "hash")).thenReturn(userEntity);
        when(userRepository.findByUserName("john")).thenReturn(Optional.empty());
        when(userRepository.findByEmail(new Email("john@weg.com"))).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber("47999999999")).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        UserCreateResponse expectedResponse = new UserCreateResponse(UUID.randomUUID(), "john", "john@weg.com", "47999999999", null);
        when(userCreateMapper.toResponse(userEntity)).thenReturn(expectedResponse);

        UserCreateResponse response = createUserCase.execute(req, id);

        assertEquals("john", response.username());
    }

    @Test
    void shouldThrowExceptionIfUserOrEmailOrPhoneAlreadyExists() {
        UserCreateRequest req = new UserCreateRequest("john", "john@weg.com", "password", "47999999999", null);
        UUID id = UUID.randomUUID();

        when(userRepository.findByUserName("john")).thenReturn(Optional.of(new User()));

        assertThrows(ValidationException.class, () -> createUserCase.execute(req, id));
    }
}
