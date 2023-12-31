package com.rc.cloud.app.operate.domain.model.productimage.valobj;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: Sort
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Sort extends ValueObject{
    private int value;

    public Sort(int value){
        this.value=value;
    }
    public Sort(){
        setValue(0);
    }

    public void setValue(int value){
        AssertUtils.assertArgumentRange(value,0,100,"Sort is not in range(0,100)");
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    public int getValue() {
        return value;
    }
}
