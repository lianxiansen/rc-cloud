package com.rc.cloud.api.product.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuRequest {

    @Schema(description = "productId")
    private String productId;

    @Schema(description = "productSkuId")
    private String productSkuId;

}
