package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserCreateMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateUserCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCreateMapper userCreateMapper;

    public CreateUserCase(UserRepository userRepository, PasswordEncoder passwordEncoder, UserCreateMapper userCreateMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCreateMapper = userCreateMapper;
    }

    @Transactional
    public UserCreateResponse execute(UserCreateRequest userCreateRequest, UUID id) {

        String hashedPassword = passwordEncoder.encode(userCreateRequest.password());

        User user = userCreateMapper.toEntity(userCreateRequest, hashedPassword);
        Email email = new Email(userCreateRequest.email());

        if(userRepository.findByUserName(userCreateRequest.username()).isPresent() ||
            userRepository.findByEmail(email).isPresent() ||
            userRepository.findByPhoneNumber(userCreateRequest.phoneNumber()).isPresent()) {

            throw new ValidationException(
                    "Username or Email or Phone Number already exists");
        }

        User savedUser = userRepository.save(user);

        return userCreateMapper.toResponse(savedUser);
    }



}

