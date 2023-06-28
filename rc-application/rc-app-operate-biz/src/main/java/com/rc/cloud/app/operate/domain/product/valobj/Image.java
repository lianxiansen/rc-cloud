package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Image extends ValueObject {
    private String url;
    public Image(String value){
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