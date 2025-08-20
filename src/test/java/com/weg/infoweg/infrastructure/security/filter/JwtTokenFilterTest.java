package com.weg.infoweg.infrastructure.security.filter;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.filter.JwtTokenFilter;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.infrastructure.security.user.UserDetailsServiceImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import jakarta.servlet.FilterChain;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenFilterTest {

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private TokenService tokenService;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private final String email = "teste@email.com";
    private final String validToken = "tokenValido";

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        userDetails = new UserDetailsImpl(
                UUID.randomUUID(),
                AccessLevel.STUDENT,
                "senhaTeste",
                email,
                "USER"
        );

        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldSetAuthentication() throws Exception {
        // Header com token Bearer
        request.addHeader("Authorization", "Bearer " + validToken);

        when(jwtTokenProvider.valideToken(validToken)).thenReturn(true);
        when(tokenService.checkValidToken(new JwtTokenDto(validToken))).thenReturn(true);
        when(jwtTokenProvider.getEmailFromJWT(validToken)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context.getAuthentication());
        assertEquals(userDetails, context.getAuthentication().getPrincipal());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_WithInvalidToken_ShouldNotSetAuthentication() throws Exception {
        request.addHeader("Authorization", "Bearer " + validToken);

        when(jwtTokenProvider.valideToken(validToken)).thenReturn(false);

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNull(context.getAuthentication());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_WithNoToken_ShouldNotSetAuthentication() throws Exception {
        // Nenhum header Authorization

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNull(context.getAuthentication());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_WhenExceptionOccurs_ShouldClearAuthentication() throws Exception {
        request.addHeader("Authorization", "Bearer " + validToken);

        when(jwtTokenProvider.valideToken(validToken)).thenThrow(new RuntimeException("Erro"));

        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNull(context.getAuthentication());

        verify(filterChain).doFilter(request, response);
    }
}
