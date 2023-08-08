package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;

import java.util.List;

/**
 * @ClassName ComfirmOrderService
 * @Author liandy
 * @Date 2023/8/2 9:21
 * @Description  确认订单服务
 * @Version 1.0
 */
public interface ComfirmOrderDomainService {
    /**
     * 购物车下单
     * @param customer
     * @return
     */
    ComfirmOrder placeOrder(Customer customer, List<Product> products, DeliveryAddress deliveryAddress);
}
