package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

/**
 * @auther Ushop
 * @date 2021/4/14 15:30
 * @Description ProductEvaluationRateVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductEvaluationRateVO {
    private int reputation;
    private int product_id;
    private String brandCommunity_brand_name;
}
