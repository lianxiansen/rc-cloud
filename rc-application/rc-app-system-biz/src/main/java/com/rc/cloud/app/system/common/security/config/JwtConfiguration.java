/**
 * @author oliveoil
 * date 2023-06-09 14:37
 */
package com.rc.cloud.app.system.common.security.config;

import com.rc.cloud.app.system.common.security.utils.DoubleJWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * jwt 配置类
 *
 * @author oliveoil
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtConfiguration {

    @Value("${rc.jwt.header}")
    private String header;

    @Value("${rc.jwt.prefix}")
    private String prefix;

    @Value("${rc.jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime;

    @Value("${rc.jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime;

    @Value("${rc.jwt.secret}")
    private String secret; // JWT 密钥

    /**
     * jwt bean
     *
     * @return jwt bean
     */
    @Bean
    public DoubleJWTUtil jwter() {
        if (accessTokenExpireTime <= 0) {
            // 一个小时
            accessTokenExpireTime = 60 * 60L;
        }
        if (refreshTokenExpireTime <= 0) {
            // 一个月
            refreshTokenExpireTime = 60 * 60 * 24 * 30L;
        }
        return new DoubleJWTUtil(secret, accessTokenExpireTime, refreshTokenExpireTime);
    }

}
