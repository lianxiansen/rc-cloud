package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.Order;

import java.util.List;

public interface SubmitOrderDomainService {
    List<Order> submitOrder(ComfirmOrder comfirmOrder);
}
