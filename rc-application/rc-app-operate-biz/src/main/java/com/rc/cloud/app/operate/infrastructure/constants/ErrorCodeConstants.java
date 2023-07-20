package com.rc.cloud.app.operate.infrastructure.constants;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @ClassName: ErrorCodeConstants
 * @Author: liandy
 * @Date: 2023/7/12 09:26
 * @Description: 通用的错误码常量
 */
public interface ErrorCodeConstants {
    ErrorCode SYSTEM_EXCEPTION = new ErrorCode(2000000000, "系统异常");
    ErrorCode OBJECT_NOT_EXISTS = new ErrorCode(2000000001, "对象不存在");
}


