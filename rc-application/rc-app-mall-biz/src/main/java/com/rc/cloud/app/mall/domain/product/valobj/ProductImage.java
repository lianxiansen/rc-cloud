package com.rc.cloud.app.mall.domain.product.valobj;

import com.rc.cloud.app.mall.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ProductImage extends AssertionConcern {
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
