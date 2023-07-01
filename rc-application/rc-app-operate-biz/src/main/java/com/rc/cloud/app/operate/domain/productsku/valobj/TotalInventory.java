package com.rc.cloud.app.operate.domain.productsku.valobj;

public class TotalInventory {


    public TotalInventory(int value){
        this.value=value;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public TotalInventory setValue(int value) {
        return new TotalInventory(value);
    }

}
