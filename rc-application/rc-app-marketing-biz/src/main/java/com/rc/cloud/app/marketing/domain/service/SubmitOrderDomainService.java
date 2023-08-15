package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;

import java.util.List;

public interface SubmitOrderDomainService {
    List<RegularOrder> submitOrder(Customer customer, ComfirmOrder comfirmOrder);
}
