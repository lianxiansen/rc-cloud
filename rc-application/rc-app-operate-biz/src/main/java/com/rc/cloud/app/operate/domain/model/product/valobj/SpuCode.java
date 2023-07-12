package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class SpuCode implements ValueObject<SpuCode> {
    private String value;
    public SpuCode(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(SpuCode other) {
        return false;
    }
}
