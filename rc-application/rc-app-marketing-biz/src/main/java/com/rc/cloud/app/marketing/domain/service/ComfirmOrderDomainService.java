package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;

import java.util.Map;

/**
 * @ClassName ComfirmOrderService
 * @Author liandy
 * @Date 2023/8/2 9:21
 * @Description  确认订单服务
 * @Version 1.0
 */
public interface ComfirmOrderDomainService {

    ComfirmOrder placeOrder(Customer customer, Product product, ProductQuality quality, DeliveryAddress deliveryAddress);

    ComfirmOrder placeOrder(Customer customer, Map<Product, ProductQuality> products, DeliveryAddress deliveryAddress);
}
