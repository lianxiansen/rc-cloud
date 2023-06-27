package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

@Data
public class ProductEvaluationCountQuery {

    private Integer productId;

    private Boolean hasPicture;

    private Integer reputation;
}
