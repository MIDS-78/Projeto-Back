package com.weg.infoweg.infrastructure.security.filter;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.infrastructure.security.user.UserDetailsServiceImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService, TokenService tokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)
                    && jwtTokenProvider.valideToken(jwt)
                    && tokenService.checkValidToken(new JwtTokenDto(jwt))) { // verifica revogação no DB

                String email = jwtTokenProvider.getEmailFromJWT(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // limpa autenticação em caso de erro
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { // espaço importante
            return bearerToken.substring(7);
        }
        return null;
    }
}
