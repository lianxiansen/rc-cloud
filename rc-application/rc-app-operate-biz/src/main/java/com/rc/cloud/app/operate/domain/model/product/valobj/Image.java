package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Image implements ValueObject<Image> {
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

    @Override
    public boolean sameValueAs(Image other) {
        return false;
    }
}
