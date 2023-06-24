package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

@Data
public class ProductEvaluationCountQuery {

    private Integer productId;

    private Boolean hasPicture;

    private Integer reputation;
}
