package com.weg.infoweg.modules.user.domain.cases;
import java.util.Optional;
import java.util.UUID;

import com.weg.infoweg.core.abstractions.UseCase;
import com.weg.infoweg.modules.user.aplication.dtos.UserDeleteRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserDeleteResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DeleteUserCase {

    @Autowired
    private final UserRepository userRepository;

    public DeleteUserCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDeleteResponse execute(UserDeleteRequest userDeleteRequest){
        User user = this.userRepository.findById(userDeleteRequest.id())
        .orElseThrow(() -> new RuntimeException("User not founded"));

        this.userRepository.deleteById(userDeleteRequest.id());

        return new UserDeleteResponse(user);
    }
}
