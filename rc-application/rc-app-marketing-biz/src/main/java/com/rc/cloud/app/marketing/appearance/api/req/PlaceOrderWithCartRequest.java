package com.rc.cloud.app.marketing.appearance.api.req;

import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import com.rc.cloud.common.core.util.BeanCopyUtils;

import java.util.List;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description 购物车下单请求
 * @Version 1.0
 */
public class PlaceOrderWithCartRequest {
    private String cartId;
    private List<String> cartItemIds;

    public PlaceOrderWithCartDTO toPlaceOrderDTO() {
        PlaceOrderWithCartDTO dto = new PlaceOrderWithCartDTO();
        BeanCopyUtils.copy(this, dto);
        return dto;
    }
}
