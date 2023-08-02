package com.rc.cloud.api.product.dto;

import lombok.Data;

@Data
public class ProductChangeEnableStatusDTO {

    public String productId;

    public boolean enableFlag;


}
