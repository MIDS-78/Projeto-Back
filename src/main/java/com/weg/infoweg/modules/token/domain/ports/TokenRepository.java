package com.weg.infoweg.modules.token.domain.ports;

import com.weg.infoweg.modules.token.domain.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {

    Optional<Token> findByToken(String token);

    void revokeTokenByTokenString(String tokenString);

    List<Token> findAllValidTokensByUserId(UUID userId);

    List<Token> findByUserIdAndIsRevokedTrue(UUID userId);

    Token save(Token token);

    Token refreshTokenByTokenString(String tokenString);

    void saveAll(List<Token> activeTokens);

    void revokeAllUserTokens(UUID userId);

}
