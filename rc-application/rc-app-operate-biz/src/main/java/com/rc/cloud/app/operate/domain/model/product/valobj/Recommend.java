package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Recommend extends ValueObject{
    private boolean flag;
    public Recommend(boolean flag){
        this.flag=flag;
    }

    public boolean getValue(){
        return flag;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
