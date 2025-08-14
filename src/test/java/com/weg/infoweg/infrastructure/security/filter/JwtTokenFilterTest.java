package com.weg.infoweg.infrastructure.security.filter;
import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Collections;

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
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        // Limpa o contexto de segurança antes de cada teste
        SecurityContextHolder.clearContext();
    }

    @Test
    public void doFilterInternal_WithValidJwtToken_ShouldSetAuthentication() throws ServletException, IOException {
        String jwtToken = "valid-jwt-token";
        String username = "usuarioTeste";
        UserDetails userDetails = new User(username, "senha", Collections.emptyList());

        // Simula o cabeçalho da requisição
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);

        // Define o comportamento dos mocks do provider e do service
        when(jwtTokenProvider.valideToken(jwtToken)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromJWT(jwtToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Chama o método a ser testado
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Verifica se a autenticação foi setada no SecurityContextHolder
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());

        // Verifica se o filterChain.doFilter foi chamado
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_WithoutJwtToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Simula uma requisição sem o cabeçalho de autorização
        when(request.getHeader("Authorization")).thenReturn(null);

        // Chama o método a ser testado
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Verifica se a autenticação NÃO foi setada
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verifica se o filterChain.doFilter foi chamado
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_WithInvalidJwtToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        String jwtToken = "invalid-jwt-token";

        // Simula o cabeçalho da requisição
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);

        // Define o mock do provider para retornar false na validação
        when(jwtTokenProvider.valideToken(jwtToken)).thenReturn(false);

        // Chama o método a ser testado
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Verifica se a autenticação NÃO foi setada
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verifica se o filterChain.doFilter foi chamado
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_OnException_ShouldClearAuthentication() throws ServletException, IOException {
        String jwtToken = "malformed-jwt-token";

        // Simula o cabeçalho da requisição
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);

        // Simula uma exceção ao tentar validar o token
        doThrow(new RuntimeException("Simulated validation error")).when(jwtTokenProvider).valideToken(jwtToken);

        // Chama o método a ser testado
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Verifica se a autenticação foi setada como null
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verifica se o filterChain.doFilter foi chamado
        verify(filterChain, times(1)).doFilter(request, response);
    }
}