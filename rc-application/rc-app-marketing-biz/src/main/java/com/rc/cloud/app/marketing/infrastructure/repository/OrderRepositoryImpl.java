package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.order.Order;
import com.rc.cloud.app.marketing.domain.entity.order.OrderRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OrderRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:38
 * @Description TODO
 * @Version 1.0
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean save(Order order) {
        return false;
    }

    @Override
    public Order findById(String id) {
        return null;
    }


}
