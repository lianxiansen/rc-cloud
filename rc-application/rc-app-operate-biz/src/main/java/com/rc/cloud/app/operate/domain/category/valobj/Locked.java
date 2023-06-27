package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Locked extends AssertionConcern {
    private boolean flag;
    public Locked(boolean flag){
        this.flag=flag;
    }
    public boolean getFlag()
    {
        return flag;
    }
}
