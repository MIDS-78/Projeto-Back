package com.weg.infoweg.infrastructure.security.user;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class UserDetailsImplTest {

    @Test
    void testUserDetailsData() {
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String password = "testpassword";

        // Crie uma instância da sua classe
        UserDetailsImpl userDetails = new UserDetailsImpl(
                id,
                AccessLevel.ADMINISTRATOR,
                password,
                username
        );

        // Verifique se os dados estão corretos
        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}
