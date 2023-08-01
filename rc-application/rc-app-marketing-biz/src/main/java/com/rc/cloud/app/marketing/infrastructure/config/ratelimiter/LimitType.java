package com.rc.cloud.app.marketing.infrastructure.config.ratelimiter;


public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求IP进行限流
     */
    IP
}
