package com.rc.cloud.app.marketing.application.service;


import com.rc.cloud.app.marketing.application.bo.DeliveryAddressBO;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressCreateDTO;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressUpdateDTO;

import java.util.List;

/**
 * @Interface DeliveryAddressApplicationService
 * @Author liandy
 * @Date 2023/8/5 15:53
 * @Description 收货地址应用服务接口
 * @Version 1.0
 */
public interface DeliveryAddressApplicationService {

    /**
     * 创建收货地址
     *
     * @param dto
     * @return
     */
    DeliveryAddressBO createDeliveryAddress(DeliveryAddressCreateDTO dto);

    /**
     * 更新收货地址
     *
     * @param dto
     * @return
     */
    DeliveryAddressBO updateDeliveryAddress(DeliveryAddressUpdateDTO dto);

    /**
     * 获取顾客收货地址
     *
     * @return
     */
    List<DeliveryAddressBO> findCustomerDeliveryAddresses();

    /**
     * 设为默认
     * @param id：收货地址唯一标识
     * @return
     */
    boolean setDeliveryAddressDefault(String id);
}
