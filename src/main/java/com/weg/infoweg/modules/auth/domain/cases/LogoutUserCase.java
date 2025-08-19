package com.weg.infoweg.modules.auth.domain.cases;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;

import java.util.UUID;

public class LogoutUserCase {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    // Injeção de dependências via construtor
    public LogoutUserCase(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    /**
     * Realiza o logout de um usuário, revogando seus tokens.
     *
     * @param userId ID do usuário que está fazendo logout
     * @param jwtTokenDto Token JWT a ser revogado
     */
    public void execute(UUID userId, JwtTokenDto jwtTokenDto) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Revoga o token enviado
        tokenService.revokeToken(jwtTokenDto);

        //revoga todos os tokens do usuário
        tokenService.revokeAllUserTokens(userId);
    }
}
