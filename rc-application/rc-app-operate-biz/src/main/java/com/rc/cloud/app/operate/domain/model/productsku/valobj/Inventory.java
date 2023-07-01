package com.rc.cloud.app.operate.domain.model.productsku.valobj;

public class Inventory {

    public Inventory(int value){
        this.value=value;
    }
    private int value;

    public int getValue() {
        return value;
    }

    public Inventory setValue(int value) {
        return new Inventory(value);
    }
}
