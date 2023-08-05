package com.rc.cloud.api.product.bo;

import lombok.Data;

@Data
public class ProductValidateBO {

    private boolean enabled;

    private  ProductAndSkuBO productSkuBO;

}
