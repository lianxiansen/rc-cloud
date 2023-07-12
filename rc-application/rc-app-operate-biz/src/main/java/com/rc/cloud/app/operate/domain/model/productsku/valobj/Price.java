package com.rc.cloud.app.operate.domain.model.productsku.valobj;


import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

import java.math.BigDecimal;

public class Price implements ValueObject<Price> {

    public static final BigDecimal MIN= BigDecimal.valueOf(0.01);
    private BigDecimal value;
    public Price(){
        this.value =MIN;
    }

    public Price(BigDecimal price){
        validate(price);
        this.value =price;
    }

    public Price(String price){
        validate(price);
       this.value= BigDecimal.valueOf(Double.valueOf(price));
    }

    public void validate(Object obj){
        if(obj==null){
            throw  new IllegalArgumentException("price must be not null");
        }
    }

    public BigDecimal getValue(){
        return value;
    }



    public void setValue(BigDecimal value){
        AssertUtils.assertArgumentRange(value,0,999999,"the value of Price is not in range(0,999999)");
        this.value=value;
    }


    @Override
    public boolean sameValueAs(Price other) {
        return false;
    }
}
