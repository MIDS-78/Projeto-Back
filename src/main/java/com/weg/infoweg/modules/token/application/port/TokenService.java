package com.weg.infoweg.modules.token.application.port;

import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface TokenService {

    JwtTokenDto refreshToken(JwtTokenDto oldRefreshTokenString);

    void revokeToken(JwtTokenDto jwtTokenDto);

    JwtTokenDto generateToken(UserDetailsImpl user);

    boolean checkValidToken(JwtTokenDto jwtTokenDto);

    List<Token> generateAndSaveTokens(User user);

    void revokeAllUserTokens(UUID userId);




}
