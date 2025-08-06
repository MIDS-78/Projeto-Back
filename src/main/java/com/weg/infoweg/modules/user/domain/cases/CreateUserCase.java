package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.core.abstractions.UseCase;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserCreateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import jakarta.transaction.Transactional;
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

        return null;
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

