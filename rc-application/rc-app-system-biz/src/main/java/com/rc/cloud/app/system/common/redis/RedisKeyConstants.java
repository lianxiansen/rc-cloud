package com.rc.cloud.app.system.common.redis;


import com.rc.cloud.common.redis.core.RedisKeyDefine;

import static com.rc.cloud.common.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;


/**
 * System Redis Key 枚举类
 *
 * @author 芋道源码
 */
public interface RedisKeyConstants {

    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码的缓存",
            "captcha_code:%s", // 参数为 uuid
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

}
