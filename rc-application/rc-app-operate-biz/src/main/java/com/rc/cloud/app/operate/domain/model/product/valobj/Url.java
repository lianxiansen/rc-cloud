package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Url extends ValueObject {

    private String value;
    public Url(String value){
        this.setValue(value);
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        if(StringUtils.isNotEmpty(value) && !StringUtils.ishttp(value)){
            throw new IllegalArgumentException("invalid url");
        }
        this.value=value;
    }
    @Override
    public boolean equals(Object other) {
        return false;
    }
}
