package com.ecommerce.service.impl;

import com.ecommerce.exception.custom.EntityNotFoundException;
import com.ecommerce.model.RefreshToken;
import com.ecommerce.model.User;
import com.ecommerce.repository.RefreshTokenRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(RefreshToken refreshToken) {
        log.info("save refresh token processing...");
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findOne(String refreshToken) {
        log.info("find refresh token by refresh token processing ...");
        return refreshTokenRepository.findByRefreshTokenAndActiveTrue(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findOneByUserId(Long userId) {
        log.info("find refresh token by user id processing ...");
        return refreshTokenRepository.findByUserIdAndActiveTrue(userId);
    }

    @Override
    public String refreshToken(String username) {
        log.info("find refresh token by username {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
        return refreshTokenRepository.findRefreshTokenByUserId(user.getId());
    }
}
