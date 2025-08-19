package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.token.domain.exceptions.TokenInvalidException;

import java.util.UUID;

public class RefleshUserCase {

    private final TokenService tokenService;

    // Injeção de dependência via construtor
    public RefleshUserCase(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Refresha o token de um usuário.
     *
     * @param userId ID do usuário
     * @param oldRefreshTokenTokenDto Token JWT de refresh antigo
     * @return novo JwtTokenDto atualizado
     */
    public JwtTokenDto execute(UUID userId, JwtTokenDto oldRefreshTokenTokenDto) {
        // Aqui você pode validar se o token é válido
        boolean valid = tokenService.checkValidToken(oldRefreshTokenTokenDto);
        if (!valid) {
            throw new TokenInvalidException("Token inválido ou expirado");
        }

        // Gera um novo token a partir do refresh token
        return tokenService.refreshToken(oldRefreshTokenTokenDto);
    }
}
