package com.rc.cloud.app.marketing.domain.service;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrder;

import java.util.List;

public interface SubmitOrderDomainService {
    List<RegularOrder> submitOrder(ComfirmOrder comfirmOrder);
}
