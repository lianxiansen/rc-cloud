package com.rc.cloud.app.marketing.infrastructure.config.ratelimiter;

import java.lang.annotation.*;
/**
 * @ClassName RateLimiter
 * @Author liandy
 * @Date 2023/7/29 15:14
 * @Description  TODO
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key前缀
     */
    String keyPrefix() default "rate_limiter:";

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
