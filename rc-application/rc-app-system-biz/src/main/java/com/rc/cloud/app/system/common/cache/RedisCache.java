package com.rc.cloud.app.system.common.cache;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis Cache
 *
 * @author oliveoil
 */
@Component
public class RedisCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认过期时长为24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**
     * 过期时长为6小时，单位：秒
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void set(String key, String value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    public Object hGet(String key, String field) {
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = stringRedisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public void hMSet(String key, Map<String, Object> map) {
        hMSet(key, map, DEFAULT_EXPIRE);
    }

    public void hMSet(String key, Map<String, Object> map, long expire) {
        stringRedisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, DEFAULT_EXPIRE);
    }

    public void hSet(String key, String field, Object value, long expire) {
        stringRedisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void expire(String key, long expire) {
        stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hDel(String key, Object... fields) {
        stringRedisTemplate.opsForHash().delete(key, fields);
    }

    public void leftPush(String key, String value) {
        leftPush(key, value, DEFAULT_EXPIRE);
    }

    public void leftPush(String key, String value, long expire) {
        stringRedisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public Object rightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }
}