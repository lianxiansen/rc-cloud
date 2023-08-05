package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

import java.math.BigDecimal;

public class Inventory extends ValueObject {

    public Inventory(int value){
        AssertUtils.assertArgumentRange(value,0,99999999,"Inventory is not in range(0,99999999)");
        this.value=value;
    }
    private int value;


    public int getValue() {
        return value;
    }

    public Inventory setValue(int value) {
        return new Inventory(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Inventory typedObject = (Inventory) other;
        }
        return false;
    }
}
