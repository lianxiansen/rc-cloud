package com.rc.cloud.app.product.domain.product.valobj;

import com.rc.cloud.app.product.domain.common.AssertionConcern;
import com.rc.cloud.app.product.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductImage extends ValueObject {
    private String url;
    public ProductImage(String value){
        this.setValue(url);
    }
    public String getValue() {
        return url;
    }
    public void setValue(String value) {
        if (null==value) {
            throw new IllegalArgumentException("商品图片url地址不为空");
        }

    }
}
