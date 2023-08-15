package com.rc.cloud.app.marketing.application.service;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.bo.RegularOrderBO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithPrductDTO;
import com.rc.cloud.app.marketing.application.dto.ComfirmOrderSubmitDTO;

import java.util.List;

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

    /**
     * 提交确认订单
     * @param dto
     * @return 常规订单
     */
    List<RegularOrderBO> submitComfirmOrder(ComfirmOrderSubmitDTO dto);
}
