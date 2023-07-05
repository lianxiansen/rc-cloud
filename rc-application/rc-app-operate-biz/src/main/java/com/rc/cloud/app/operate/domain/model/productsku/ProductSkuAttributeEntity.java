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


import com.rc.cloud.app.operate.domain.model.product.ProductAttributeValueEntity;

public class ProductSkuAttributeEntity  implements Comparable<ProductSkuAttributeEntity> {

    private String attribute;

    private String attributeValue;

    private  Integer sort;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public int hashCode() {
        return this.getAttribute().hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(ProductSkuAttributeEntity o) {
        if(o.sort<this.sort){
            return 1;
        }else{
            return -1;
        }
    }
}
