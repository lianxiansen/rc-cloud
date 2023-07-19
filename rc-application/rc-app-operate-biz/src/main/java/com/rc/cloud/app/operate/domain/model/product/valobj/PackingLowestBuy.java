package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;


/**
 * 按照装箱数购买
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
