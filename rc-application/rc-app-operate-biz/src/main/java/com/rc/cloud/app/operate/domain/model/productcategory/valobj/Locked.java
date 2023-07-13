package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Locked extends ValueObject{
    private boolean flag;
    public Locked(boolean flag){
        this.flag=flag;
    }
    public boolean getFlag()
    {
        return flag;
    }
    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Locked typedObject = (Locked) other;
        }
        return false;
    }
}
