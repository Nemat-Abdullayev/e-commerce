package com.ecommerce.service;

import com.ecommerce.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    void saveOrUpdate(RefreshToken refreshToken);

    Optional<RefreshToken> findOne(String refreshToken);

    Optional<RefreshToken> findOneByUserId(Long userId);

    String refreshToken(String username);
}
