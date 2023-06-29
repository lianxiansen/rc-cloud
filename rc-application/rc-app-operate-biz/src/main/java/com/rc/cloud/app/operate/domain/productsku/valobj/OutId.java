package com.rc.cloud.app.operate.domain.productsku.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class OutId extends ValueObject {
    private String value;
    public OutId(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
