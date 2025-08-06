package com.weg.infoweg.modules.user.domain.ports;

import com.weg.infoweg.modules.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id);

}

