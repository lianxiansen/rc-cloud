package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class SkuCode extends ValueObject {
    private String value;
    public SkuCode(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
