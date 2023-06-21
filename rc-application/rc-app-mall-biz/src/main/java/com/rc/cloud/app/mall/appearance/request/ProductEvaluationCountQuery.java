package com.rc.cloud.app.mall.appearance.request;

import lombok.Data;

@Data
public class ProductEvaluationCountQuery {

    private Integer productId;

    private Boolean hasPicture;

    private Integer reputation;
}
