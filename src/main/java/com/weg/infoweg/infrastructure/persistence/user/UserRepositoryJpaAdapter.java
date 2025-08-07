package com.weg.infoweg.infrastructure.persistence.user;


import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;

    public UserRepositoryJpaAdapter(UserRepositoryJpa userRepositoryJpa){
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<User> findById(UUID id){
        return userRepositoryJpa.findById(id);
    }

    @Override
    public void deleteById(UUID id){
        userRepositoryJpa.deleteById(id);
    }

    @Override
    public User save(User user){
        return userRepositoryJpa.save(user);
    }

    @Override
    public Optional<User> findByUserName (String username){
        return userRepositoryJpa.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail (String email){
        return userRepositoryJpa.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhoneNumber (String phoneNumber){
        return userRepositoryJpa.findByPhoneNumber(phoneNumber);
    }

}