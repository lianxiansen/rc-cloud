package com.rc.cloud.app.marketing.domain.entity.order;

/**
 * @Interfaces OrderRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  订单资源库
 * @Version 1.0
 */
public interface OrderRepository {
    /**
     * 保存
     * @param order
     * @return
     */
    boolean save(Order order);

    /**
     * 查找订单
     * @param id
     * @return
     */
    Order findById(String id);


}
