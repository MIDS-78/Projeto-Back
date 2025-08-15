package com.weg.infoweg.modules.token.domain.service;

import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.token.domain.ports.TokenRepository;
import com.weg.infoweg.modules.user.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> generateAndSaveTokens(User user) {

        revokeAllUserTokens(user.getId());

        String accessTokenString = "access_token_" + UUID.randomUUID();
        String refreshTokenString = "refresh_token_" + UUID.randomUUID();

        Token refreshToken = new Token();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenString);
        refreshToken.setTokenType(TokenType.REFRESH);
        refreshToken.setExpiresAt(LocalDateTime.now().plusHours(1));
        tokenRepository.save(refreshToken);

        Token accessToken = new Token();
        accessToken.setUser(user);
        accessToken.setToken(accessTokenString);
        accessToken.setTokenType(TokenType.ACCESS);
        accessToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        return List.of(accessToken, refreshToken);
    }

    public void revokeAllUserTokens(UUID userId) {
        List<Token> revokedTokens = tokenRepository.findByUserIdAndIsRevokedTrue(userId);
        revokedTokens.forEach(token -> {
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }

    public Token refreshToken(String oldRefreshTokenString) {
        return tokenRepository.refreshTokenByTokenString(oldRefreshTokenString);
    }
}
