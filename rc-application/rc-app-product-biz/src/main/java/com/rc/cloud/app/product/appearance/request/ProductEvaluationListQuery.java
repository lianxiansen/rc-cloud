package com.rc.cloud.app.product.appearance.request;


import lombok.Data;

@Data
public class ProductEvaluationListQuery extends PageVO {

    private Integer product_id;

    private Integer type;

    private Integer reputation;

    private Integer order_by_albums;
}
