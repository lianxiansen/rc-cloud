package com.rc.cloud.app.marketing.appearance.api.req;

import lombok.Data;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description 商品下单请求
 * @Version 1.0
 */
@Data
public class PlaceOrderWithPrductRequest {
    private String productId;
    private String productItemId;
}
