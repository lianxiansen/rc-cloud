package com.rc.cloud.app.marketing.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-25 13:54
 * @description TODO
 */
@Data
@Schema(description = "购物车 APP - 购物车创建 Request VO")
public class CartDTO {
    @Schema(description = "产品Id")
    private String productid;
    @Schema(description = "产品属性唯一Id")
    private String productuniqueid;
    @Schema(description = "数量")
    private Integer num;
    @Schema(description = "店铺Id")
    private String shopid;
}
