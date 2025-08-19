package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.domain.exceptions.AuthenticationValidationException;
import com.weg.infoweg.modules.token.application.port.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginUserCase {

    private final AuthenticationManager authenticationManager;

    private final TokenService jwtService;

    public LoginUserCase(AuthenticationManager authenticationManager, TokenService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public JwtTokenDto execute(@Valid UserLoginRequest userLogin) throws AuthenticationValidationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));

        if(!authentication.isAuthenticated()){
            throw new AuthenticationValidationException("Invalid credentials");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtService.generateToken(userDetails);
    }
}
