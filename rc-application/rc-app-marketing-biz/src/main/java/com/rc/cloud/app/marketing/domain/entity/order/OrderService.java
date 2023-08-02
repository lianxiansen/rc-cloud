package com.rc.cloud.app.marketing.domain.entity.order;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderService
 * @Author liandy
 * @Date 2023/7/29 08:54
 * @Description 订单聚合服务
 * @Version 1.0
 */
@Service
public class OrderService {
    public void pay(Order order){

    }

    public List<Order> findOrdersByTradeNo(String tradeNo) {
        return null;
    }
}
