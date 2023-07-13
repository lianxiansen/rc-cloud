package com.rc.cloud.app.distributor.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author WJF
 * @create 2023-06-27 14:51
 * @description TODO
 */
@Configuration
public class DistributorAutoConfig {

    /**
     * 解码器
     * @return 解码器
     */
    @Bean("webPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
