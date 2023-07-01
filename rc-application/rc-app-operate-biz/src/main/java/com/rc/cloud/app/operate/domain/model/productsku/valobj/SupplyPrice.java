package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

import java.math.BigDecimal;

public class SupplyPrice extends ValueObject {

    public static final BigDecimal MIN= BigDecimal.valueOf(0.01);
    private BigDecimal value;
    public SupplyPrice(){
        this.value =MIN;
    }

    public SupplyPrice(BigDecimal price){
        this.value =price;
    }

    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        this.assertArgumentRange(value,0,999999,"the value of SupplyPrice is not in range(0,999999)");
        this.value=value;
    }

}
