package com.rc.cloud.common.core.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.rc.cloud.common.core.exception.FailedException;

/**
 * 校验工具类
 *
 * @author oliveoil
 */
public class AssertUtils {

    public static void isBlank(String str, String variable) {
        if (StrUtil.isBlank(str)) {
            throw new FailedException(variable + "不能为空");
        }
    }

    public static void isNull(Object object, String variable) {
        if (object == null) {
            throw new FailedException(variable + "不能为空");
        }
    }

    public static void isArrayEmpty(Object[] array, String variable) {
        if(ArrayUtil.isEmpty(array)){
            throw new FailedException(variable + "不能为空");
        }
    }
}