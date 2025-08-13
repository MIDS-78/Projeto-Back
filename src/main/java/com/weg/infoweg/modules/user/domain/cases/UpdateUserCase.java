package com.weg.infoweg.modules.user.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserUpdateMapper;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateRequest;
import com.weg.infoweg.modules.user.aplication.dtos.UserUpdateResponse;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateUserCase {

    private final UserRepository userRepository;

    private final UserUpdateMapper userUpdateMapper;

    public UpdateUserCase(UserRepository userRepository, UserUpdateMapper userUpdateMapper) {
        this.userRepository = userRepository;
        this.userUpdateMapper = userUpdateMapper;
    }


    @Transactional
    public void execute(UserUpdateRequest request, UUID id) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));

        userUpdateMapper.toEntity(request , existingUser);

        userRepository.save(existingUser);
    }
}
