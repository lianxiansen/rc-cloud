package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class SpuCode extends ValueObject{
    private String value;
    public SpuCode(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
