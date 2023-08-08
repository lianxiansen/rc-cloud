package com.rc.cloud.app.marketing.application.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description TODO
 * @Version 1.0
 */
@Data
public class PlaceOrderWithCartDTO {
    private String cartId;
    private List<String> cartItemIds;
}
