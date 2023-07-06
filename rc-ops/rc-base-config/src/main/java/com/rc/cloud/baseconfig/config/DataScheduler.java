package com.rc.cloud.baseconfig.config;

import com.rc.cloud.baseconfig.amap.AmapWebapi;
import com.rc.cloud.baseconfig.amap.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;


/**
 * @author WJF
 * @create 2023-06-26 13:21
 * @description TODO
 */
@Component
public class DataScheduler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 2 * * ?")
    public void getAllProvinceCity() {
        String redisKey = RedisContants.provinceCityRedisKey;
        //获取高德地图开放平台接口，获取省市区3级地区
        List<District> districtList = AmapWebapi.getAllProvinceCity();

        redisTemplate.opsForValue().set(redisKey, districtList, Duration.ofDays(1));
    }
}
