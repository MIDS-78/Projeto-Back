package com.weg.infoweg.infrastructure.api.service;

import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAuthenticationServiceImplTest {

    @Test
    void getAuthenticatedUserId_ReturnsCorrectId() {
        // Mocks
        SecurityContext securityContext = mock(SecurityContext.class);

        // Crie um UserDetails personalizado para o teste
        UUID uuid = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(
                uuid, AccessLevel.STUDENT, "password","testuser"
        );

        // Simule o objeto de autenticação
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Defina o comportamento dos mocks
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Crie a instância do serviço que você vai testar
        UserAuthenticationService service = new UserAuthenticationServiceImpl();

        // Chame o método e verifique se o ID está correto
        UUID userId = service.getIdUserAuthentication();
        assertEquals(uuid, userId);
    }

}
