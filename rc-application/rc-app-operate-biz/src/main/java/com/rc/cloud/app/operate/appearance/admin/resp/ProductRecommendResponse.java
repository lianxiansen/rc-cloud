package com.rc.cloud.app.operate.appearance.admin.resp;


import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品推荐response")
public class ProductRecommendResponse {
    @Schema(description = "产品推荐唯一标识")
    private String Id;
    @Schema(description = "产品唯一标识")
    private String productId;
    @Schema(description = "推荐产品唯一标识")
    private String recommendProductId;
    @Schema(description = "推荐产品名称")
    private String recommendProductName;
    @Schema(description = "推荐产品图片")
    private String recommendProductImage;

    public static ProductRecommendResponse from(ProductRecommendBO bo) {
        ProductRecommendResponse response = ProductRecommendConvert.INSTANCE.convert2ProductRecommendResponse(bo);
        return response;
    }

    public static List<ProductRecommendResponse> from(List<ProductRecommendBO> boList) {
        List<ProductRecommendResponse> vos = new ArrayList<>();
        boList.forEach(bo->{
            vos.add(from(bo));
        });
        return vos;
    }

}
