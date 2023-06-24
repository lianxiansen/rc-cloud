package com.rc.cloud.app.product.domain.common;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Deleted extends AssertionConcern {
    private boolean flag;
    public boolean getFlag(){
        return flag;
    }
}
