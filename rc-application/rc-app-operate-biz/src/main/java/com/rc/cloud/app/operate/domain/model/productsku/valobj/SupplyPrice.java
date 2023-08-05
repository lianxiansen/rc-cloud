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

    public SupplyPrice(String price){
        if(StringUtils.isEmpty(price)){
            this.value=BigDecimal.ZERO;
        }
        validate(price);
        this.value= BigDecimal.valueOf(Double.valueOf(price));
    }

    public void validate(String obj){
        if(obj!=null){
            if(!StringUtils.isNumeric(obj)){
                throw  new IllegalArgumentException("price must be double");
            }
        }

    }


    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        if(value!=null){
            validate(value.toString());
        }
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
