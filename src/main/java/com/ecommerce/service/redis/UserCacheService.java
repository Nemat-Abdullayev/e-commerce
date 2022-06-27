package com.ecommerce.service.redis;

import com.ecommerce.model.redis.UserInfo;

public interface UserCacheService {

    void add(UserInfo userInfo);

    UserInfo findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}
