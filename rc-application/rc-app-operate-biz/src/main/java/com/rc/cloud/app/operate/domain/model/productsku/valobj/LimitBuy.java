package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

public class LimitBuy extends ValueObject{

    private boolean limitFlag;

    private int value;

    public  LimitBuy(int value){
        if(value<=0){
            limitFlag=false;
        }else{
            limitFlag=true;
        }
        value=value;
    }

    public int getValue(){
        return value;
    }

    public boolean isLimitFlag() {
        return limitFlag;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Inventory typedObject = (Inventory) other;
        }
        return false;
    }
}
