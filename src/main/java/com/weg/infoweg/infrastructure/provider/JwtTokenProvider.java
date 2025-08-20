package com.weg.infoweg.infrastructure.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private int jwtExpirationInMs;

    // Gera token de acesso usando email
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        String email = ((com.weg.infoweg.infrastructure.security.user.UserDetailsImpl) userDetails).getEmail();

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    // Gera refresh token usando email
    public String generateRefreshToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        String email = ((com.weg.infoweg.infrastructure.security.user.UserDetailsImpl) userDetails).getEmail();

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    // Recupera email do token JWT
    public String getEmailFromJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject();
    }

    // Valida token JWT
    public boolean valideToken(String authToken){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException exception){
            // Logar o erro para depuração se necessário
        }
        return false;
    }

    public int getJwtExpirationInMs() {
        return jwtExpirationInMs;
    }
}
