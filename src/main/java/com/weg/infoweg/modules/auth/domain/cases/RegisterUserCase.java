package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.infrastructure.persistence.user.mapper.UserRegisterMapper;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.domain.exceptions.EmailInvalidException;
import com.weg.infoweg.modules.auth.domain.exceptions.UsernameInvalidException;
import com.weg.infoweg.modules.auth.domain.ports.PasswordEncoderCrypto;
import com.weg.infoweg.modules.auth.domain.ports.PasswordValidator;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserCase {

    private final UserRepository repository;

    private final PasswordEncoderCrypto encoder;

    private final UserRegisterMapper userRegisterMapper;

    private final PasswordValidator passwordValidator;

    public RegisterUserCase(UserRepository repository, PasswordEncoderCrypto encoder, UserRegisterMapper userRegisterMapper, PasswordValidator passwordValidator) {
        this.repository = repository;
        this.encoder = encoder;
        this.userRegisterMapper = userRegisterMapper;
        this.passwordValidator = passwordValidator;
    }

    public UserRegisterResponse execute(UserRegisterRequest userRegister) {
        validateRegistrationData(userRegister);

        passwordValidator.isValid(userRegister.password());

        User user = userRegisterMapper.toEntity(userRegister);
        user.setPasswordHash(encoder.encryptPassword(user.getPasswordHash()));

        repository.save(user);
        return new UserRegisterResponse("User register with sucessfully");
    }

    private void validateRegistrationData(UserRegisterRequest userRegister) {
        checkIfEmailIsUnique(userRegister);

        checkIfUsernameIsUnique(userRegister);

    }


    private void checkIfEmailIsUnique(UserRegisterRequest userRegister) {
        Email email = new Email(userRegister.email());
        if (repository.findByEmail(email).isPresent()) {
            throw new EmailInvalidException("Email is already in use");
        }
    }

    private void checkIfUsernameIsUnique(UserRegisterRequest userRegister) {
        if (repository.findByUserName(userRegister.username()).isPresent()) {
            throw new UsernameInvalidException("Username is already in use");
        }
    }

}
