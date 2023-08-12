package com.rc.cloud.api.product.dto;

import lombok.Data;

@Data
public class ProductChangeRecommendStatusDTO {

    public String productId;

    public boolean recommendFlag;


}
