//package com.rc.cloud.app.operate.infrastructure.config;
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
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;
//
//    @Value("${spring.redis.password}")
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
