package com.weg.infoweg.modules.auth.aplication.dtos.login;

import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;

public record UserLoginResponse(JwtTokenDto jwtTokenDto) {
}
