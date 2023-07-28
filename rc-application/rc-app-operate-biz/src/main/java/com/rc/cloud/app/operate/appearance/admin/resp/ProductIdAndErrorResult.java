package com.rc.cloud.app.operate.appearance.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductIdAndErrorResult {
    @Schema(description = "移除失败id")
    private String productId;

    @Schema(description = "失败原因")
    private String errorMsg;

    public ProductIdAndErrorResult(String productId, String errorMsg) {
        this.productId = productId;
        this.errorMsg = errorMsg;
    }
}
