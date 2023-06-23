//package com.rc.cloud.app.mall.infrastructure.config;
//
//import cn.hutool.core.util.StrUtil;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author:chenjianxiang
// * @Date 2021/4/1
// * @Description:
// */
//@Configuration
//public class RedissonConfig {
//
//    @Value("${spring.redis.main.host}")
//    private String host;
//
//    @Value("${spring.redis.main.port}")
//    private String port;
//
//    @Value("${spring.redis.main.password}")
//    private String password;
//
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        if(StrUtil.isNotEmpty(password)){
//            config.useSingleServer().setAddress(String.format("redis://%s:%s",host,port)).setPassword(password);
//        }else{
//            config.useSingleServer().setAddress(String.format("redis://%s:%s",host,port));
//        }
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }
//}
