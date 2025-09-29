package com.weg.infoweg.infrastructure.persistence.token.mappers;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import com.weg.infoweg.modules.token.domain.Token;
import com.weg.infoweg.modules.token.domain.enums.TokenType;
import com.weg.infoweg.modules.user.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenMapper {

    public Token toEntity(JwtTokenDto jwtTokenDto, User user, TokenType tokenType, LocalDateTime expiresAt){
        return new Token(jwtTokenDto.token(), tokenType, user, expiresAt);
    }
}
