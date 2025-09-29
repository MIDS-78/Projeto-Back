package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserGetMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class GetUserCase {

    private final UserRepository userRepository;

    private final UserGetMapper userGetMapper;

    public GetUserCase(UserRepository userRepository, UserGetMapper userGetMapper) {
        this.userRepository = userRepository;
        this.userGetMapper = userGetMapper;
    }

    public UserGetResponse execute(UserGetRequest userGetRequest) {
        User user = userRepository.findById(userGetRequest.id()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userGetMapper.toResponse(user);
    }
}
