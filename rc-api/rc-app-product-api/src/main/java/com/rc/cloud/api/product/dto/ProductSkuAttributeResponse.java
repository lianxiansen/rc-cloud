package com.rc.cloud.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU规格详情")
public class ProductSkuAttributeResponse {


    @Schema(description = "商品属性名")
    private String name;

    @Schema(description = "商品属性值")
    private String value;

    @Schema(description = "排序")
    private int sort;



}
