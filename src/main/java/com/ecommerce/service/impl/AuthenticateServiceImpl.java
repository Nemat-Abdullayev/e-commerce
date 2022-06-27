package com.ecommerce.service.impl;

import com.ecommerce.dto.request.AuthRequest;
import com.ecommerce.dto.request.RefreshTokenDTO;
import com.ecommerce.dto.response.AuthResponseDTO;
import com.ecommerce.exception.custom.AuthenticationException;
import com.ecommerce.exception.custom.InvalidRefreshTokenException;
import com.ecommerce.exception.custom.RefreshTokenExpiredException;
import com.ecommerce.exception.custom.SystemException;
import com.ecommerce.model.RefreshToken;
import com.ecommerce.model.User;
import com.ecommerce.model.redis.UserInfo;
import com.ecommerce.security.JwtTokenUtil;
import com.ecommerce.service.AuthenticateService;
import com.ecommerce.service.RefreshTokenService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.redis.UserCacheService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserCacheService userCacheService;

    @Override
    @SneakyThrows
    public ResponseEntity<?> generateToken(AuthRequest authRequest) {
        log.info("Enter inputs of authentication request {}", authRequest);
        String username = authRequest.getUsername();
        authenticate(username, authRequest.getPassword());
        final User user = userService.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found by this email [ " + username + " ]"));
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findOneByUserId(user.getId());

        if (optionalRefreshToken.isPresent()) {

            String refreshToken = optionalRefreshToken.get().getRefreshToken();
            UserInfo userInfo = userCacheService.findByRefreshToken(refreshToken);

            if (Objects.nonNull(userInfo)) {
                try {
                    boolean notExpired = !jwtTokenUtil.isTokenExpired(userInfo.getAccessToken());
                    if (notExpired)
                        throw new SystemException("There is already an active session using your account");
                } catch (ExpiredJwtException e) {
                    userCacheService.deleteByRefreshToken(refreshToken);
                }
            }
        }


        log.debug("generating token");
        final String accessToken = jwtTokenUtil.generateAccessToken(user);

        final String refreshToken = jwtTokenUtil.generateRefreshToken();
        RefreshToken refreshTokenEntity = RefreshToken.builder().build();
        if (optionalRefreshToken.isPresent()) {
            refreshTokenEntity = optionalRefreshToken.get();
            log.info("refresh token is exist, operation is update");
            modifyRefreshTokenEntity(user, accessToken, refreshToken, refreshTokenEntity);
        } else {
            log.info("refresh token not exist,operation is insert");
            modifyRefreshTokenEntity(user, accessToken, refreshToken, refreshTokenEntity);
        }
        refreshTokenService.saveOrUpdate(refreshTokenEntity);

        log.info("saving logged user to redis cache");
        UserInfo cacheUserInfo = UserInfo.builder()
                .id(user.getId())
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        userCacheService.add(cacheUserInfo);

        return ResponseEntity.ok(
                AuthResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
    }

    private void modifyRefreshTokenEntity(User user, String accessToken,
                                          String refreshToken,
                                          RefreshToken refreshTokenEntity) {
        refreshTokenEntity.setActive(true);
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenEntity.setRefreshToken(refreshToken);
        refreshTokenEntity.setAccessToken(accessToken);
        refreshTokenEntity.setExpiredDateTime(LocalDateTime.now().plusHours(4));
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenDTO refreshTokenDTO) {
        log.info("get access token by refresh token if exist and valid");
        RefreshToken refreshTokenEntity = refreshTokenService.findOne(
                refreshTokenDTO.getRefreshToken()).orElseThrow(
                () -> new InvalidRefreshTokenException("refresh token is invalid"));
        if (Objects.nonNull(refreshTokenEntity)) {
            Long userID = refreshTokenEntity.getUserId();
            User user = userService.findUserById(userID).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("user not found with %s this id", userID)));

            String username = user.getUsername();
//            UserInfo userInfoFromRedisCache = userInfoCacheRepository.findByUsername(username);
//
//            if (Objects.nonNull(userInfoFromRedisCache)) {
//                if (!jwtTokenUtil.isTokenExpired(userInfoFromRedisCache.getAccessToken())) {
//                    throw new SystemException("There is already an active session using your account");
//                }
//                userInfoCacheRepository.deleteByUsername(username);
//            }
            if (refreshTokenEntity.getExpiredDateTime().isBefore(LocalDateTime.now())) {
                log.error(String.format("refresh token expired in %s time",
                        refreshTokenEntity.getExpiredDateTime()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:ss"))));
                throw new RefreshTokenExpiredException(String.format("refresh token expired in %s time",
                        refreshTokenEntity.getExpiredDateTime()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:ss"))));
            }

            final String accessToken = jwtTokenUtil.generateAccessToken(user);
            final String refreshToken = jwtTokenUtil.generateRefreshToken();
            refreshTokenEntity.setAccessToken(accessToken);
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenEntity.setExpiredDateTime(LocalDateTime.now().plusHours(4));
            refreshTokenService.saveOrUpdate(refreshTokenEntity);

//            log.info("saving logged user to redis cache");
//            UserInfo cacheUserInfo = UserInfo.builder()
//                    .id(user.getId())
//                    .username(username)
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .build();
//            userInfoCacheRepository.save(cacheUserInfo);

            return new ResponseEntity<>(
                    AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build(),
                    HttpStatus.OK);
        }
        throw new InvalidRefreshTokenException("refresh token is null");
    }

    private void authenticate(String username, String password) {
        if (Objects.nonNull(password) && Objects.nonNull(username)) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (DisabledException e) {
                log.error("USER_DISABLED", e);
                throw new AuthenticationException("USER_DISABLED");
            } catch (BadCredentialsException e) {
                log.error("invalid credentials entered - {}", e.getClass());
                throw new AuthenticationException("INVALID_CREDENTIALS");
            }
        }

    }
}