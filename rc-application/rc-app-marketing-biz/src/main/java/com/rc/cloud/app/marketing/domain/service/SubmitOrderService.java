package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.Order;

public interface SubmitOrderService {
    Order createOrder(ComfirmOrder comfirmOrder);
}
