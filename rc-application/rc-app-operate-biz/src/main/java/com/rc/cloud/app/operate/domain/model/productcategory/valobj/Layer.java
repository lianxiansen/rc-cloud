package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Layer extends ValueObject {
    public static final int ROOT =1;
    private int value;
    public Layer(){
        this.value = ROOT;
    }
    public Layer(int level){
        setValue(level);
    }
    public int getValue(){
        return value;
    }

    private void setValue(int value){
        AssertUtils.assertArgumentRange(value,1,10,"the value of Layer is not in range(1,3)");
        this.value=value;
    }

    public Layer addLayer(Layer layer){
        return new Layer(this.value+layer.getValue());
    }

    public Layer increment(){
        return new Layer(this.value+1);
    }

    public Layer addLayer(int value){
        return new Layer(this.value+value);
    }


    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Layer typedObject = (Layer) other;
            return this.value==typedObject.value;
        }
        return false;
    }
}
