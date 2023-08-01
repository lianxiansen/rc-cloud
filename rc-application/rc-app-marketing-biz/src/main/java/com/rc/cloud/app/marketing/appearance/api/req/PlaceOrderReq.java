package com.rc.cloud.app.marketing.appearance.api.req;

import com.rc.cloud.app.marketing.application.dto.PlaceOrderDTO;
import com.rc.cloud.common.core.util.BeanCopyUtils;

import java.util.List;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description TODO
 * @Version 1.0
 */
public class PlaceOrderReq {
    private String cartId;
    private List<String> cartItemIds;

    public PlaceOrderDTO toPlaceOrderDTO() {
        PlaceOrderDTO dto = new PlaceOrderDTO();
        BeanCopyUtils.copy(this, dto);
        return dto;
    }
}
