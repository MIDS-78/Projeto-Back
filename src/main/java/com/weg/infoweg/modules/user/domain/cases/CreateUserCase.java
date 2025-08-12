package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.core.abstractions.UseCase;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserCase implements UseCase<UserCreateRequest, UserCreateResponse> {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCreateResponse execute(UserCreateRequest userCreateRequest) {

        String hashedPassword = passwordEncoder.encode(userCreateRequest.password());

        User user = toEntity(userCreateRequest, hashedPassword);

        if(userRepository.findByUserName(userCreateRequest.username()).isPresent() ||
            userRepository.findByEmail(userCreateRequest.email()).isPresent() ||
            userRepository.findByPhoneNumber(userCreateRequest.phoneNumber()).isPresent()) {

            throw new ValidationException(
                    "Username or Email or Phone Number already exists");
        }

        User savedUser = userRepository.save(user);

        return new UserCreateResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail().toString(),
                savedUser.getPhoneNumber(),
                savedUser.getAccessLevel()
        );
    }

    public User toEntity(UserCreateRequest userCreateRequest, String hashedPassword) {

        final var user = new User();

        user.setUsername(userCreateRequest.username());
        user.setEmail(userCreateRequest.email());
        user.setPasswordHash(hashedPassword);
        user.setPhoneNumber(userCreateRequest.phoneNumber());
        user.setAccessLevel(userCreateRequest.accessLevel());

        return user;
    }

}

