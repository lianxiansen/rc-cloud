package com.rc.cloud.common.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ClassName Factory
 * @Author liandy
 * @Date 2023/7/24 8:45
 * @Description  工厂类注解 被注解的工厂类可以被spring容器管理
 * @see Component
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Factory {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
