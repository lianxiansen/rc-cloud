package com.rc.cloud.api.product.dto;

import lombok.Data;

@Data
public class ProductChangePublicStatusDTO {

    public String productId;

    public boolean publicFlag;


}
