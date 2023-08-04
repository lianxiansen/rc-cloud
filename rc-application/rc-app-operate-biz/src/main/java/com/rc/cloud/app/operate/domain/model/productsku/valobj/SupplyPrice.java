package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

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
    public SupplyPrice(String price){
        validate(price);
        this.value= BigDecimal.valueOf(Double.valueOf(price));
    }

    public void validate(String obj){
        if(obj==null){
            throw  new IllegalArgumentException("price must be not null");
        }
        if(!StringUtils.isNumeric(obj)){
            throw  new IllegalArgumentException("price must be double");
        }
    }


    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        AssertUtils.assertArgumentRange(value,0,999999,"the value of SupplyPrice is not in range(0,999999)");
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            SupplyPrice typedObject = (SupplyPrice) other;
        }
        return false;
    }
}
