package com.weg.infoweg.infrastructure.persistence.user;

import com.weg.infoweg.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, UUID> {
}
