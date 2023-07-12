package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Sort implements ValueObject<Sort> {
    private int value;
    public Sort(){
        this(0);
    }
    public Sort(int value){
        setValue(value);
    }

    private void setValue(int value){
        validate(value);
        this.value=value;

    }

    private void validate(int value){
        AssertUtils.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
    }

    public int getValue(){
        return value;
    }

    @Override
    public boolean sameValueAs(Sort other) {
        return false;
    }
}
