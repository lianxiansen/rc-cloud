package com.rc.cloud.baseconfig.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.baseconfig.amap.AmapWebapi;
import com.rc.cloud.baseconfig.config.RedisContants;
import com.rc.cloud.baseconfig.service.DistrictService;
import com.rc.cloud.common.redis.core.RedisKeyDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.rc.cloud.common.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * @author WJF
 * @create 2023-06-26 11:04
 * @description TODO
 */

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object getAllProvinceCity() {
        String redisKey = RedisContants.provinceCityRedisKey;
        Object provinceCity = redisTemplate.opsForValue().get(redisKey);
        if (provinceCity != null) {
            return provinceCity;
        }
        //获取高德地图开放平台接口，获取中国以下省市区3级地区
        provinceCity = AmapWebapi.getAllProvinceCity();
        redisTemplate.opsForValue().set(redisKey, provinceCity, Duration.ofDays(1));
        return provinceCity;
    }

}
