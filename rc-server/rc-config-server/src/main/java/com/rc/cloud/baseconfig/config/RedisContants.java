package com.rc.cloud.baseconfig.config;

import com.rc.cloud.common.redis.core.RedisKeyDefine;

import static com.rc.cloud.common.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * @author WJF
 * @create 2023-06-26 13:26
 * @description TODO
 */

public class RedisContants {
    final static String provinceCityKey = "getAllProvinceCity";
    private static final RedisKeyDefine district_service = new RedisKeyDefine("行政区域缓存",
            "districtservice:%s", // 参数为 uuid
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    public static String provinceCityRedisKey = formatKey(provinceCityKey);

    private static String formatKey(String biz) {
        return String.format(district_service.getKeyTemplate(), biz);
    }
}
