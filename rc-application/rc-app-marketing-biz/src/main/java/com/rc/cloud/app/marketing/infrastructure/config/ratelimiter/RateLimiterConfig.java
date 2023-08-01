package com.rc.cloud.app.marketing.infrastructure.config.ratelimiter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @ClassName RedisConfig
 * @Author liandy
 * @Date 2023/7/29 15:18
 * @Description TODO
 * @Version 1.0
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate1(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
