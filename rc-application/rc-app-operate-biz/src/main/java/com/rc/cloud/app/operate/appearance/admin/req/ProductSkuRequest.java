package com.rc.cloud.app.operate.appearance.admin.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductSkuRequest {

    @Schema(description = "productId")
    private String productId;

    @Schema(description = "productSkuId")
    private String productSkuId;

}
