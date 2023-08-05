package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Remark extends ValueObject{
    private String value;
    public Remark(String value){
        this.setValue(value);
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        if(StringUtils.isNotEmpty(value) && value.length()>1000){
            throw new IllegalArgumentException(" The remark length cannot be greater than 1000");
        }
        this.value=value;
    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
