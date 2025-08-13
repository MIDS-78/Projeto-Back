package com.weg.infoweg.modules.user.domain.service;

import com.weg.infoweg.modules.user.aplication.dtos.*;
import com.weg.infoweg.modules.user.domain.cases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private CreateUserCase createUserCase;
    private UpdateUserCase updateUserCase;
    private DeleteUserCase deleteUserCase;
    private GetUserCase getUserCase;
    private UserServiceImpl service;

    @BeforeEach
    void setup() {
        createUserCase = mock(CreateUserCase.class);
        updateUserCase = mock(UpdateUserCase.class);
        deleteUserCase = mock(DeleteUserCase.class);
        getUserCase = mock(GetUserCase.class);
        service = new UserServiceImpl(createUserCase, updateUserCase, deleteUserCase, getUserCase);
    }

    @Test
    void shouldDelegateToCreateUserCase() {
        UUID id = UUID.randomUUID();
        UserCreateRequest req = new UserCreateRequest("john", "john@weg.com", "password1234", "47999999999", null);
        service.createUser(req, id);
        verify(createUserCase).execute(req, id);
    }

    @Test
    void shouldDelegateToDeleteUserCase() {
        UserDeleteRequest req = new UserDeleteRequest(UUID.randomUUID());
        service.deleteUser(req);
        verify(deleteUserCase).execute(req);
    }
}
