package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Locked implements ValueObject<Locked> {
    private boolean flag;
    public Locked(boolean flag){
        this.flag=flag;
    }
    public boolean getFlag()
    {
        return flag;
    }

    @Override
    public boolean sameValueAs(Locked other) {
        return false;
    }
}
