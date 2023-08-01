package com.rc.cloud.app.marketing.application.service;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderDTO;

public interface OrderApplicationService {

    ComfirmOrderBO placeOrder(PlaceOrderDTO placeOrderDTO);
}
