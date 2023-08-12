package com.rc.cloud.app.operate.application.bo;

import lombok.Data;

@Data
public class ProductValidateBO {

    private boolean enabled;

    private  ProductAndSkuBO productSku;

}
