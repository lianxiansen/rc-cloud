package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Open extends ValueObject {
    private boolean flag;

    public Open(boolean flag){
        this.flag=flag;
    }
}
