package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Tag extends ValueObject {
    private String value;
    public Tag(String value){
        //检验标签是否合法
        value=value;
    }
    public String getValue() {
        return value;
    }

}
