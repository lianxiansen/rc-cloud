package com.rc.cloud.app.marketing.application.service;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithPrductDTO;

/**
 * @Interface OrderApplicationService
 * @Author liandy
 * @Date 2023/8/8 13:51
 * @Description  订单应用服务
 * @Version 1.0
 */
public interface OrderApplicationService {

    /**
     * 购物车下单
     * @param placeOrderDTO
     * @return
     */
    ComfirmOrderBO placeOrderWithCart(PlaceOrderWithCartDTO placeOrderDTO);

    /**
     * 商品下单
     * @param placeOrderDTO
     * @return
     */
    ComfirmOrderBO placeOrderWithProduct(PlaceOrderWithPrductDTO placeOrderDTO);
}
