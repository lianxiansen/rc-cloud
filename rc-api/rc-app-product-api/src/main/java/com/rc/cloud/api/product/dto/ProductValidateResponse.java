package com.rc.cloud.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品SKU详情")
public class ProductValidateResponse {

    @Schema(description = "是否可以购买")
    private boolean enabled;

    @Schema(description = "SKU详情")
    private ProductValidateSkuDetailResponse productSku;



}
