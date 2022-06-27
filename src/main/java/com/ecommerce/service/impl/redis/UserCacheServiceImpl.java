package com.ecommerce.service.impl.redis;

import com.ecommerce.model.redis.UserInfo;
import com.ecommerce.service.redis.UserCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private final RedisTemplate<String, UserInfo> redisTemplate;


    @Override
    public void add(UserInfo userInfo) {
        log.info("add user data to cache {}", userInfo);
        redisTemplate
                .opsForValue()
                .set(userInfo.getRefreshToken(), userInfo, Duration.ofHours(2));
    }

    @Override
    public UserInfo findByRefreshToken(String refreshToken) {
        log.info("find user from cache by refresh token");
        return redisTemplate.opsForValue().get(refreshToken);
    }

    @Override
    public void deleteByRefreshToken(String refreshToken) {
        log.info("delete user from cache");
        redisTemplate.delete(refreshToken);
    }
}
