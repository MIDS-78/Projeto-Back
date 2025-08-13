package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.modules.user.aplication.dtos.UserGetRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserGetResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;

public class GetUserCase {

    private final UserRepository userRepository;

    public GetUserCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserGetResponse execute(UserGetRequest userGetRequest) {
        User user = userRepository.findById(userGetRequest.id()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserGetResponse(user.getId(), user.getUsername(), user.getEmail().toString(), user.getPhoneNumber(), user.getAccessLevel());
    }
}
