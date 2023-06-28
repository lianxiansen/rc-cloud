package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;
import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Enable extends ValueObject {
    private boolean value;
    public Enable(){
        this.value = true;
    }
    public Enable(boolean flag){
        this.value = flag;
    }

    public boolean result(){
        return value;
    }

}
