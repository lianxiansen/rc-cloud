package com.rc.cloud.api.product.bo;

import lombok.Data;

@Data
public class ProductValidateBO {

    private String productId;

    private String skuId;

    private boolean enabled;

    private  ProductAndSkuBO productSku;

}
