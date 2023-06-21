package com.rc.cloud.app.mall.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/18
 * @Description:
 */
@Configuration
public class RedisTemplateConfig {
    //order
    @Value("${spring.redis.main.host}")
    private String mainHost;

    @Value("${spring.redis.main.port}")
    private String mainPort;

    @Value("${spring.redis.main.password}")
    private String mainPassword;

    //user
    @Value("${spring.redis.log.host}")
    private String logHost;

    @Value("${spring.redis.log.port}")
    private String logPort;

    @Value("${spring.redis.log.password}")
    private String logPassword;

    private static final int MAX_IDLE = 200; //最大空闲连接数
    private static final int MAX_TOTAL = 1024; //最大连接数
    private static final long MAX_WAIT_MILLIS = 10000; //建立连接最长等待时间


    //配置工厂
    public RedisConnectionFactory connectionFactory(String host, int port, String password, int maxIdle,
                                                    int maxTotal, long maxWaitMillis, int index) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);

        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }

        if (index != 0) {
            jedisConnectionFactory.setDatabase(index);
        }

        jedisConnectionFactory.setPoolConfig(poolConfig(maxIdle, maxTotal, maxWaitMillis, false));
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    //连接池配置
    public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }


    //------------------------------------
    @Bean(name = "redisTemplate")
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(
                connectionFactory(mainHost, Integer.parseInt(mainPort), mainPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 1));
        return template;
    }

    //------------------------------------
    @Bean(name = "redisLogTemplate")
    public StringRedisTemplate redisLogTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(
                connectionFactory(logHost, Integer.parseInt(logPort), logPassword, MAX_IDLE, MAX_TOTAL, MAX_WAIT_MILLIS, 0));
        return template;
    }

}
