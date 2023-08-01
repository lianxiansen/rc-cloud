package com.rc.cloud.app.marketing.infrastructure.config.ratelimiter;

import com.rc.cloud.common.core.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName RateLimiterAspect
 * @Author liandy
 * @Date 2023/7/29 15:13
 * @Description TODO
 * @Version 1.0
 */
@Aspect
@Component
public class RateLimiterAspect {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisScript<Long> redisScript;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        String keyPrefix = rateLimiter.keyPrefix();
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String redisKey = getRedisKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(redisKey);
        Long number = redisTemplate.execute(redisScript, keys, count, time);
        if (number == null || number.intValue() > count) {
            throw new RuntimeException("访问过于频繁，请稍候再试");
        }

    }

    public String getRedisKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.keyPrefix());
        if (rateLimiter.limitType() == LimitType.IP) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            stringBuffer.append(IpUtils.getIpAddr(request)).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }
}
