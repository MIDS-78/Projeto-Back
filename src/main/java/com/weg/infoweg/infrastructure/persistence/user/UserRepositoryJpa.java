package com.weg.infoweg.infrastructure.persistence.user;

import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(Email email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}