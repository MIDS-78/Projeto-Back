package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserGetMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetUserAuthenticationCase {

    private final UserRepository userRepository;

    private final UserGetMapper userGetMapper;

    public GetUserAuthenticationCase(UserRepository userRepository, UserGetMapper userGetMapper) {
        this.userRepository = userRepository;
        this.userGetMapper = userGetMapper;
    }

    public UserGetResponse execute(UUID uuid){
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userGetMapper.toResponse(user);
    }
}
