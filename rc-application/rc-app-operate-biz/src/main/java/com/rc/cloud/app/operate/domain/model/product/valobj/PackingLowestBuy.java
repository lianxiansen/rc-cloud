package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;


/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class PackingLowestBuy extends ValueObject {


    private boolean value;
    public PackingLowestBuy(){
        this.value = true;
    }
    public PackingLowestBuy(boolean flag){
        this.value = flag;
    }

    public boolean result(){
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
