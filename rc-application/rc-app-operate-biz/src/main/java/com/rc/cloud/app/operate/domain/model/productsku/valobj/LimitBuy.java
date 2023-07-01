package com.rc.cloud.app.operate.domain.model.productsku.valobj;

public class LimitBuy {

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
}
