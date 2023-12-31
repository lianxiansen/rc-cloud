package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Weight extends ValueObject{

    public static final BigDecimal MIN= BigDecimal.valueOf(0.01);
    private BigDecimal value;
    public Weight(){
        this.value =MIN;
    }


    public Weight(String weight){
        if(StringUtils.isEmpty(weight)){
            this.value=BigDecimal.ZERO;
        }else{
            validate(weight);
            this.value= BigDecimal.valueOf(Double.valueOf(weight));
        }
    }

    public void validate(String obj){
        if(!StringUtils.isNumeric(obj)){
            throw  new IllegalArgumentException("Weight must be double");
        }
    }

    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        AssertUtils.assertArgumentRange(value,0,999999,"the value of Weight is not in range(0,999999)");
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Weight typedObject = (Weight) other;
        }
        return false;
    }
}
