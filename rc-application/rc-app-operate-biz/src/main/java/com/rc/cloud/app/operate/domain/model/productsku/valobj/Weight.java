package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

import java.math.BigDecimal;

public class Weight extends ValueObject {

    public static final BigDecimal MIN= BigDecimal.valueOf(0.01);
    private BigDecimal value;
    public Weight(){
        this.value =MIN;
    }

    public Weight(BigDecimal price){
        this.value =price;
    }

    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        this.assertArgumentRange(value,0,999999,"the value of Weight is not in range(0,999999)");
        this.value=value;
    }

}
