package com.rc.cloud.app.operate.domain.model.productsku.valobj;


import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

import java.math.BigDecimal;

public class Price extends ValueObject{

    public static final BigDecimal MIN= BigDecimal.valueOf(0.01);
    private BigDecimal value;
    public Price(){
        this.value =MIN;
    }

    public Price(String price){
        if(StringUtils.isEmpty(price)){
            throw  new IllegalArgumentException("price must be not null");
        }else{
            validate(price);
            this.value= BigDecimal.valueOf(Double.valueOf(price));
        }
    }

    public void validate(String price){
        if(!StringUtils.isNumeric(price)){
            throw  new IllegalArgumentException("Price must be double");
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
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Inventory typedObject = (Inventory) other;
        }
        return false;
    }
}
