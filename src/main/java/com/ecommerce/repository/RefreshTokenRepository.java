package com.ecommerce.repository;

import com.ecommerce.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenAndActiveTrue(String refreshToken);

    Optional<RefreshToken> findByUserIdAndActiveTrue(Long userId);

    @Query(value = "select rt.refreshToken from RefreshToken rt where rt.id=:userId and rt.active=true")
    String findRefreshTokenByUserId(Long userId);
}
