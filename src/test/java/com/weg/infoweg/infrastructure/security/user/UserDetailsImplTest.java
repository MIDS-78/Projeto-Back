package com.weg.infoweg.infrastructure.security.user;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class UserDetailsImplTest {

    @Test
    void testUserDetailsData() {
        UUID id = UUID.randomUUID();
        String email = "testuser@weg.net";
        String username = "testuser";
        String password = "testpassword";

        // Crie uma instância da sua classe
        UserDetailsImpl userDetails = new UserDetailsImpl(
                id,
                AccessLevel.ADMINISTRATOR,
                password,
                email,
                username
        );

        // Verifique se os dados estão corretos
        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(AccessLevel.ADMINISTRATOR));

    }
}
