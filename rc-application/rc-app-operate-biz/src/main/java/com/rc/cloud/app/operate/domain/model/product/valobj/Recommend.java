package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Recommend implements ValueObject<Recommend> {
    private boolean flag;
    public Recommend(boolean flag){
        this.flag=flag;
    }

    public boolean getValue(){
        return flag;
    }

    @Override
    public boolean sameValueAs(Recommend other) {
        return false;
    }
}
