    package com.weg.infoweg.infrastructure.persistence.token;

    import com.weg.infoweg.modules.token.domain.Token;
    import jakarta.transaction.Transactional;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    public interface TokenRepositoryJpa extends JpaRepository<Token, UUID> {

        Optional<Token> findByToken(String token);

        @Modifying
        @Transactional
        @Query("UPDATE Token t SET t.isRevoked = true WHERE t.token = :tokenString")
        void revokeTokenByTokenString(@Param("tokenString") String tokenString);

        @Query("SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id " +
                "WHERE u.id = :userId AND t.isRevoked = false AND t.expiresAt >= CURRENT_TIMESTAMP")
        List<Token> findAllValidTokensByUserId(@Param("userId") UUID userId);

        List<Token> findByUserIdAndIsRevokedTrue(UUID userId);

        Token refreshTokenByTokenString(@Param("tokenString") String tokenString);
    }
