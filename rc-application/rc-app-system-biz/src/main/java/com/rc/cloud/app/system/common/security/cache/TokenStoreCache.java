package com.rc.cloud.app.system.common.security.cache;

import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

/**
 * 认证 Cache
 *
 * @author oliveoil
 */
@Component
public class TokenStoreCache {
    @Resource
    private RedisCache redisCache;

    @Min(5L)
    @Value("${rc.jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime = 60 * 60L; // Access Token 过期时间，单位秒

    @Min(3600L)
    @Value("${rc.jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime = 30 * 24 * 3600L; // Refresh Token 过期时间，单位秒

    // JWT 密钥
    public void saveJwtAccessToken(String accessToken, String username) {
        saveJwtToken(RedisKeys.getAccessTokenKey(username), accessToken, accessTokenExpireTime);
    }

    public void saveJwtRefreshToken(String refreshToken, String username) {
        saveJwtToken(RedisKeys.getRefreshTokenKey(username), refreshToken, refreshTokenExpireTime);

    }

    private void saveJwtToken(String key, String token, long expireTime) {
        redisCache.set(key, token, expireTime);
    }

    public void deleteJwtAccessTokenByUsername(String username) {
        redisCache.delete(RedisKeys.getAccessTokenKey(username));
    }

    public void deleteJwtRefreshTokenByUsername(String username) {
        redisCache.delete(RedisKeys.getRefreshTokenKey(username));
    }

    public boolean validateHasJwtAccessToken(String accessToken, String username) {
        String accessTokenKey = RedisKeys.getAccessTokenKey(username);
        Object redisAccessToken = redisCache.get(accessTokenKey);
        if (redisAccessToken == null) {
            return false;
        }
        return redisAccessToken.equals(accessToken);
    }

    public boolean validateHasJwtRefreshToken(String refreshToken, String username) {
        String refreshTokenKey = RedisKeys.getRefreshTokenKey(username);
        Object redisRefreshToken = redisCache.get(refreshTokenKey);
        if (redisRefreshToken == null) {
            return false;
        }
        return redisRefreshToken.equals(refreshToken);
    }
}
