package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

import java.util.List;

/**
 * @Interfaces DeliveryAddressRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  收货地址资源库
 * @Version 1.0
 */
public interface DeliveryAddressRepository {
    /**
     * 保存
     * @param deliveryAddress
     * @return
     */
    boolean save(DeliveryAddress deliveryAddress);


    DeliveryAddress findById(String id);

    List<DeliveryAddress> findByCustomerId(String customerId);
}
