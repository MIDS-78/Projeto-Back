package com.weg.infoweg.infrastructure.persistence.token;

import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.exceptions.TokenException;
import com.weg.infoweg.modules.token.domain.ports.TokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenRepositoryJpaAdapter implements TokenRepository {

    private final TokenRepositoryJpa tokenRepositoryJpa;

    public TokenRepositoryJpaAdapter(TokenRepositoryJpa tokenRepositoryJpa) {
        this.tokenRepositoryJpa = tokenRepositoryJpa;
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepositoryJpa.findByToken(token);
    }

    @Override
    public List<Token> findAllValidTokensByUserId(UUID userId) {
        return tokenRepositoryJpa.findAllValidTokensByUserId(userId);
    }

    @Override
    public List<Token> findByUserIdAndIsRevokedTrue(UUID userId) {
        return tokenRepositoryJpa.findByUserIdAndIsRevokedTrue(userId);
    }

    @Override
    public void revokeTokenByTokenString(String tokenString) {
        tokenRepositoryJpa.revokeTokenByTokenString(tokenString);
    }

    @Override
    public Token save(Token token) {
        return tokenRepositoryJpa.save(token);
    }

    @Override
    public Token refreshTokenByTokenString(String tokenString) {
        return tokenRepositoryJpa.findByToken(tokenString).orElseThrow(() -> new TokenException("Token not found"));
    }

    @Override
    public void saveAll(List<Token> activeTokens) {
        tokenRepositoryJpa.saveAll(activeTokens);
    }

    @Override
    public void revokeAllUserTokens(UUID userId){
        tokenRepositoryJpa.revokeAllUserTokens(userId);
    }


}
