package com.rc.cloud.app.operate.appearance.admin.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品组合项response")
public class ProductGroupItemResponse {
    @Schema(description = "产品组合项唯一标识")
    private String Id;
    @Schema(description = "产品唯一标识")
    private String productId;
    @Schema(description = "产品名称")
    private String productName;
    @Schema(description = "产品图片")
    private String productImage;
    @Schema(description = "创建时间")
    private String createTime;
}
