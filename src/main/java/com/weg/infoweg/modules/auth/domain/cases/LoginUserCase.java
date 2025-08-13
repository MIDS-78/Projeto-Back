package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.modules.auth.aplication.dtos.JwtTokenDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.domain.exceptions.AuthenticationValidationException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginUserCase {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtService;

    public LoginUserCase(AuthenticationManager authenticationManager, JwtTokenProvider jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public JwtTokenDto execute(@Valid UserLoginRequest userLogin) throws AuthenticationValidationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));

        if(authentication.isAuthenticated()){
            throw new AuthenticationValidationException("Invalid credentials");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);
        return new JwtTokenDto(token);
    }
}
