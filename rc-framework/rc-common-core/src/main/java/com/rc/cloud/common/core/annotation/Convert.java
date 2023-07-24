package com.rc.cloud.common.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ClassName Convert
 * @Author liandy
 * @Date 2023/7/24 8:45
 * @Description  转换器类注解 被注解的转换器类可以被spring容器管理
 * @see Component
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Convert {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
