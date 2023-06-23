package com.rc.cloud.app.mall.infrastructure.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/10
 * @Description:
 */
@Slf4j
@Component
public class RedisUtil {

    @Resource(name = "redisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 执行结果标识
     */
    private static final Long SUCCESS = 1L;

    /**
     * 设置成功标识
     */
    private static final String LOCK_SUCCESS = "OK";

    /**
     * key不存在时才设置值
     */
    private static final String SET_IF_NOT_EXIST = "NX";

    /**
     * 过期时间单位标识，EX：秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    /**
     * 锁的过期时间(单位：秒)
     */
    private static final int LOCK_EXPIRE_TIME = 60;

    /**
     * 最大尝试次数
     */
    private static final int MAX_ATTEMPTS = 100;

    @Resource(name = "redissonClient")
    private RedissonClient redissonClient;



    public void putString(String redisKey, String value){
        stringRedisTemplate.opsForValue().set(redisKey, value);
    }

    public void putString(String redisKey, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(redisKey, value, timeout, timeUnit);
    }


    public String getString(String redisKey) {
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    public String getString(String redisKey, String hashKey) {
        Object redis = stringRedisTemplate.opsForHash().get(redisKey, hashKey);
        if (ObjectUtil.isEmpty(redis)) {
            return "";
        }
        return redis.toString();
    }

    public <T> T getObject(String redisKey, Class<T> clazz) {
        String redisString = stringRedisTemplate.opsForValue().get(redisKey);
        if (StrUtil.isEmptyOrUndefined(redisString)) {
            return null;
        }
        return RedisJson.parseObject(redisString, clazz);
    }

    public <T> T getObjectFromHash(String redisKey, int id, Class<T> clazz) {
        Object obj = stringRedisTemplate.opsForHash().get(redisKey, String.valueOf(id));
        if (ObjectUtil.isEmpty(obj)) {
            return null;
        }
        return RedisJson.parseObject(JSONUtil.toJsonStr(obj), clazz);
    }

    public <T> List<T> getListFromHash(String redisKey, int id, Class<T> clazz) {
        Object obj = stringRedisTemplate.opsForHash().get(redisKey, String.valueOf(id));
        if (ObjectUtil.isEmpty(obj)) {
            return null;
        }
        return RedisJson.parseList(obj.toString(), clazz);
    }

    public <T> List<T> multiGetListFromHash(String redisKey, List<Integer> ids, Class<T> clazz) {
        List<String> idStr = ids.stream().map(Object::toString).collect(Collectors.toList());
        List<Object> list = stringRedisTemplate.opsForHash().multiGet(redisKey, Arrays.asList(idStr.toArray()));
        return RedisJson.parseList(list.toString(), clazz);
    }

    public <T> T getHash(String redisKey, long id, Class<T> clazz) {
        Object a = stringRedisTemplate.opsForHash().get(redisKey, String.valueOf(id));
        return a != null ? RedisJson.parseObject(a.toString(), clazz) : null;
    }

    public <T> T getHash(String redisKey, String id, Class<T> clazz) {
        Object a = stringRedisTemplate.opsForHash().get(redisKey, String.valueOf(id));
        return a != null ? RedisJson.parseObject(a.toString(), clazz) : null;
    }

    public <T> List<T> getListFromHashList(String redisKey, Class<T> clazz) {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(redisKey);
        List<T> result = new ArrayList<>();
        if (map != null) {
            map.forEach((key, value) -> {
                String v = (String) value;
                T t = RedisJson.parseObject(v, clazz);
                result.add(t);
            });
        }
        return result;
    }
    public Boolean deleteHash(String redisKey){
        return stringRedisTemplate.delete(redisKey);
    }

    public Long deleteFromHash(String redisKey, int id) {
        return stringRedisTemplate.opsForHash().delete(redisKey, String.valueOf(id));
    }

    public Long deleteFromHash(String redisKey, String id) {
        return stringRedisTemplate.opsForHash().delete(redisKey, id);
    }

    public Long multiDeleteFromHash(String redisKey, List<Integer> ids) {
        String[] idArrays = ids.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[0]);
        return stringRedisTemplate.opsForHash().delete(redisKey, idArrays);
    }

    public void putHash(String redisKey, int id, String value) {
        stringRedisTemplate.opsForHash().put(redisKey, String.valueOf(id), value);
    }

    /**
     * 分值
     * @param redisKey
     * @param value
     * @param score
     */
    public void putZSet(String redisKey,String value,long score) {
        stringRedisTemplate.opsForZSet().add(redisKey,value,score);
    }
    /**
     * 分值范围拿
     * @param redisKey
     * @param min
     * @param max
     */
    public Set<String> getZSetRangeByScore(String redisKey, long min, long max) {
       return stringRedisTemplate.opsForZSet().rangeByScore(redisKey, Convert.toDouble(min),Convert.toDouble(max));
    }
    /**
     * 分页
     * @param redisKey
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getZSetRangeByPage(String redisKey, int pageIndex, int pageSize,Boolean isDesc) {
        long start = (long) (pageIndex - 1) * pageSize;
        long end = start + pageSize - 1;
        if(BooleanUtil.isTrue(isDesc)){
            return stringRedisTemplate.opsForZSet().reverseRangeWithScores(redisKey, start,end);
        }else {
            return stringRedisTemplate.opsForZSet().rangeWithScores(redisKey, start,end);
        }
    }

    /**
     *
     * @param redisKey
     * @return Count
     */
    public long getZSetCount(String redisKey) {
        return stringRedisTemplate.opsForZSet().size(redisKey);
    }

    public <T> void putHash(String redisKey, int id, T t) {
        stringRedisTemplate.opsForHash().put(redisKey, String.valueOf(id), RedisJson.toJSONString(t));
    }

    public <T> void putHash(String redisKey, String id, T t) {
        stringRedisTemplate.opsForHash().put(redisKey, id, RedisJson.toJSONString(t));
    }

    public <T extends BaseEntity> void putHash(String redisKey, List<T> list) {
        for (T t : list
        ) {
            stringRedisTemplate.opsForHash().put(redisKey, String.valueOf(t.getId()), RedisJson.toJSONString(t));
        }
    }

    public void multiPutHash(String redisKey, Map<String, Object> map) {
        stringRedisTemplate.opsForHash().putAll(redisKey, map);
    }

    public <T extends BaseEntity> void multiPutHash(String redisKey, List<T> list) {
        Map<String, Object> map = new HashMap<>();
        for (T item: list) {
            map.put(String.valueOf(item.getId()), RedisJson.toJSONString(item));
        }
        multiPutHash(redisKey, map);
    }

    public long getHashCount(String redisKey) {
        return stringRedisTemplate.opsForHash().size(redisKey);
    }

    /**
     * 获取锁
     * @param key
     * @return
     */
    public Boolean getLock(String key, String value) {
        return tryLock(key, value,MAX_ATTEMPTS);
    }

    /**
     * 尝试加锁
     * @param key
     * @param maxAttempts 重试次数
     * @return
     */
    public Boolean tryLock(String key, String value, Integer maxAttempts) {
        int attemptCounter = 0;
        while (attemptCounter < maxAttempts) {
            if (setLock(key, value, LOCK_EXPIRE_TIME)) {
                return Boolean.TRUE;
            }
            attemptCounter++;
            if (attemptCounter >= maxAttempts) {
                log.error("获取锁失败！, " + key);
            }
            try {
                // 休眠时间100毫秒
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("获取锁等待异常：{}",e.getMessage());
            }
        }

        return Boolean.FALSE;
    }

    /**
     * 加锁 key不存在时才设置key value
     * @param lockKey   加锁键
     * @param value  	加锁客户端唯一标识
     * @param seconds   锁过期时间
     * @return
     */
    public Boolean setLock(String lockKey, String value, long seconds) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
//            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
//            String result = jedis.set(lockKey, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds);
//            if (LOCK_SUCCESS.equals(result)) {
//                return Boolean.TRUE;
//            }
            return Boolean.FALSE;
        });
    }

    /**
     * 解锁
     * @param key	加锁键
     * @param value
     * @return
     */
    public boolean unLock(String key, String value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            List<String> keys = Arrays.asList(key, value);
            Object result = stringRedisTemplate.execute(redisScript, keys);
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("释放Redis锁异常：", e);
        }
        return false;
    }



    /**
     * 存入带时效的缓存
     *
     * @param redisKey
     * @param jsonReids
     * @param seconds
     */
    public void putHashByTimes(String redisKey, String jsonReids, long seconds) {
        stringRedisTemplate.opsForValue().set(redisKey, jsonReids, seconds, TimeUnit.SECONDS);

    }

    /**
     * 直接删除带时效的缓存
     *
     * @param redisKey
     */
    public Boolean deleteHashByTimes(String redisKey) {
        return stringRedisTemplate.delete(redisKey);
    }

    /**
     * 获取缓存
     *
     * @param redisKey
     * @return
     */
    public String getStringByTimes(String redisKey) {
        return stringRedisTemplate.opsForValue().get(redisKey) + "";
    }

    public Set<String> keys(String pattern){
       return stringRedisTemplate.keys(String.format("%s:*", pattern));
    }

    public Set<String> keysWithOutMark(String pattern){
        return stringRedisTemplate.keys(String.format("%s*", pattern));
    }

    public Boolean acquireLock(String lockKey, Long seconds, Function<RLock, Boolean> function) {
        Boolean result = false;
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(seconds, TimeUnit.SECONDS);
        try {
            result = function.apply(lock);
        } catch (Exception e) {
            log.debug(e.toString());
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void setSortedSet(String key, String value, double score) {
        stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    public Long getSortedSetCount(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().count(key, min, max);
    }
}
