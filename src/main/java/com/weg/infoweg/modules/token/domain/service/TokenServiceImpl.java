package com.weg.infoweg.modules.token.domain.service;

import com.weg.infoweg.infrastructure.provider.JwtTokenProvider;
import com.weg.infoweg.infrastructure.security.user.UserDetailsImpl;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.application.port.TokenService;
import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.token.domain.ports.TokenRepository;
import com.weg.infoweg.modules.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// O TokenServiceImpl gerencia a lógica de negócio para a criação, validação e revogação de tokens de autenticação.
// Ele utiliza o JwtTokenProvider para lidar com a parte criptográfica dos tokens e o TokenRepository para persistência.
@Service
public class TokenServiceImpl implements TokenService {


    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // A dependência do JwtTokenProvider deve ser injetada no construtor
    public TokenServiceImpl(TokenRepository tokenRepository, JwtTokenProvider jwtTokenProvider) {
        this.tokenRepository = tokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Este método é responsável por gerar e persistir tokens de acesso e refresh.
    // Lógica esperada:
    // 1. Chamar 'revokeAllUserTokens' para invalidar tokens antigos do usuário.
    // 2. Usar 'jwtTokenProvider.generateToken' para criar um novo token de acesso e um de refresh.
    // 3. Persistir os dois tokens no banco de dados.
    // 4. Retornar os tokens criados.
    @Override
    public List<Token> generateAndSaveTokens(User user) {
        revokeAllUserTokens(user.getId());

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getAccessLevel(),
                user.getPasswordHash(),
                user.getEmail()
        );

        String accessToken = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        Token accessTokenEntity = new Token(accessToken, TokenType.ACCESS, user);
        Token refreshTokenEntity = new Token(refreshToken, TokenType.REFRESH, user);

        tokenRepository.save(accessTokenEntity);
        tokenRepository.save(refreshTokenEntity);

        return List.of(accessTokenEntity, refreshTokenEntity);
    }


    // Este método invalida todos os tokens ativos de um usuário.
    // Lógica esperada:
    // 1. Encontrar todos os tokens que pertencem ao usuário e que não estão revogados.
    // 2. Para cada token encontrado, mudar o status de revogado para 'true'.
    // 3. Salvar as alterações no banco de dados.
    @Override
    public void revokeAllUserTokens(UUID userId) {
        List<Token> activeTokens = tokenRepository.findAllValidTokensByUserId(userId);

        if (activeTokens.isEmpty()) {
            return;
        }

        activeTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(activeTokens);
    }

    // Este método invalida um token específico.
    // Lógica esperada:
    // 1. Encontrar o token no banco de dados usando o seu valor (a string do token).
    // 2. Se o token for encontrado, marcar o status de revogado para 'true'.
    // 3. Salvar a alteração no banco de dados.
    @Override
    public void revokeToken(JwtTokenDto jwtTokenDto) {
        Optional<Token> optionalToken = tokenRepository.findByToken(jwtTokenDto.token());

        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }

    // Este método gera apenas um token de acesso para o usuário.
    // Lógica esperada:
    // 1. Usar 'jwtTokenProvider.generateToken' para criar o token.
    // 2. Retornar o DTO do token gerado.
    @Override
    public JwtTokenDto generateToken(User user) {

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getAccessLevel(),
                user.getPasswordHash(),
                user.getEmail()
        );

        String accessToken = jwtTokenProvider.generateToken(userDetails);
        return new JwtTokenDto(accessToken);
    }

    // Este método verifica se um token é válido para uso.
    // Lógica esperada:
    // 1. Chamar 'jwtTokenProvider.valideToken' para verificar a assinatura e a expiração do token.
    // 2. Consultar o banco de dados para verificar se o token existe e não foi revogado.
    // 3. Retornar 'true' apenas se ambas as verificações forem bem-sucedidas.
    @Override
    public boolean checkValidToken(JwtTokenDto jwtTokenDto) {
        String token = jwtTokenDto.token();

        boolean isJwtValid = jwtTokenProvider.valideToken(token);
        if (!isJwtValid) {
            return false;
        }

        Optional<Token> tokenFromDb = tokenRepository.findByToken(token);
        if (tokenFromDb.isEmpty() || tokenFromDb.get().isRevoked()) {
            return false;
        }
        return true;
    }

    // Este método é responsável por renovar um token de acesso usando um token de refresh válido.
    // Lógica esperada:
    // 1. Encontrar o token de refresh no banco de dados usando a string do token.
    // 2. Se o token de refresh for válido, gerar um novo token de acesso para o usuário.
    // 3. Revogar o token de refresh antigo para garantir que ele não seja reutilizado.
    // 4. Retornar o novo token de acesso.
    @Override
    public JwtTokenDto refreshToken(JwtTokenDto oldRefreshTokenDto) {
        String oldRefreshToken = oldRefreshTokenDto.token();

        Optional<Token> optionalRefreshToken = tokenRepository.findByToken(oldRefreshToken);

        if (optionalRefreshToken.isEmpty()) {
            throw new RuntimeException("Refresh token inválido ou não encontrado");
        }

        Token oldRefreshTokenEntity = optionalRefreshToken.get();

        User user = oldRefreshTokenEntity.getUser();
        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getAccessLevel(),
                user.getPasswordHash(),
                user.getEmail()
        );

        String newAccessToken = jwtTokenProvider.generateToken(userDetails);

        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        Token newRefreshTokenEntity = new Token(newRefreshToken, TokenType.REFRESH, user);
        tokenRepository.save(newRefreshTokenEntity);

        oldRefreshTokenEntity.setRevoked(true);
        tokenRepository.save(oldRefreshTokenEntity);

        return new JwtTokenDto(newAccessToken);
    }
}