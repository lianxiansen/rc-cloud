package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Locked extends ValueObject {
    private boolean flag;
    public Locked(boolean flag){
        this.flag=flag;
    }
    public boolean getFlag()
    {
        return flag;
    }
}
