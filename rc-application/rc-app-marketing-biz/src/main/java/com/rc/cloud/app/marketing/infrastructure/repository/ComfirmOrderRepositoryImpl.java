package com.rc.cloud.app.marketing.infrastructure.repository;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ComfirmOrderRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:38
 * @Description 确认订单资源库
 * @Version 1.0
 */
@Repository
public class ComfirmOrderRepositoryImpl implements ComfirmOrderRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean save(ComfirmOrder comfirmOrder) {
        redisTemplate.opsForValue().set("COMFIRM_ORDER"+ StrUtil.COLON + comfirmOrder.getId(), JSONObject.toJSONString(comfirmOrder), 600, TimeUnit.SECONDS);
        return true;
    }


    @Override
    public ComfirmOrder findById(String id){
        String json= (String) redisTemplate.opsForValue().get("COMFIRM_ORDER"+ StrUtil.COLON +id);
        ComfirmOrder comfirmOrder= JSONObject.parseObject(json, ComfirmOrder.class);
        return comfirmOrder;
    }
}
