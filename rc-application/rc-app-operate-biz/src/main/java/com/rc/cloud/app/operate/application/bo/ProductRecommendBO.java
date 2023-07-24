package com.rc.cloud.app.operate.application.bo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductRecommendBO {

    private String Id;
    private String productId;
    private String recommendProductId;
    private String recommendProductName;
    private String recommendProductImage;

}
