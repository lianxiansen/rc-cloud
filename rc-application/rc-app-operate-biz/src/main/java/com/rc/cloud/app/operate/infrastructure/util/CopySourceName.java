package com.rc.cloud.app.operate.infrastructure.util;

import java.lang.annotation.*;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/8
 * @Description:
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CopySourceName {

    String value();

}
