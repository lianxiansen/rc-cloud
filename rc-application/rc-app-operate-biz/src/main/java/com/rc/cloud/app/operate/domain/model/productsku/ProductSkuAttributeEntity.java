package com.rc.cloud.app.operate.domain.model.productsku;



//        -- 商品SKU属性表
//        -- 存储[
//        -- {
//        --                     "attribute_name" : "颜色",
//        --                     "attribute_value" : "卡其色",
//        --                     "sort_id" : 42
//        --                 },
//        --                 {
//        --                     "attribute_name" : "尺码",
//        --                     "attribute_value" : "44",
//        --                     "sort_id" : 42
//        --                 }
//        --             ]


public class ProductSkuAttributeEntity {

    private String attribute;

    private String attributeValue;

    private  Integer sortId;
}
