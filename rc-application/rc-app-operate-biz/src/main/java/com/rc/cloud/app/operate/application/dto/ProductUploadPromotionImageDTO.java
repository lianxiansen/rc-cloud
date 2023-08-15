package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "上传商品推广图请求数据")
public class ProductUploadPromotionImageDTO {

    public String productId;


    @Schema(description = "推广图")
    public String promotionImage;


}
