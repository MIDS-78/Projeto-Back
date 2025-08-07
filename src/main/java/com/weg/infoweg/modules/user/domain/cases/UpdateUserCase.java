package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UpdateUserCase {

    private final UserRepository userRepository;

    public UpdateUserCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void execute(UserUpdateRequest request) {

        Optional<User> existingUserOpt = userRepository.findById(request.id());
        if (existingUserOpt.isEmpty()) {
            throw new UserNotFoundException("User with ID " + request.id() + " not found.");
        }

        User existingUser = existingUserOpt.get();

        existingUser.setUsername(request.name());
        existingUser.setEmail(request.email());

        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setUpdatedBy(1);

        userRepository.save(existingUser);
    }
}
