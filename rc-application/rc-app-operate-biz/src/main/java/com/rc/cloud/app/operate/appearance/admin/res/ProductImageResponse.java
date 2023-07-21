package com.rc.cloud.app.operate.appearance.admin.res;


import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品图片response")
public class ProductImageResponse {

    @Schema(description = "图片url")
    private String url;

    @Schema(description = "图片sort")
    private int sort;

    public ProductImageResponse(String url, int sort) {
        this.url = url;
        this.sort = sort;
    }
}
