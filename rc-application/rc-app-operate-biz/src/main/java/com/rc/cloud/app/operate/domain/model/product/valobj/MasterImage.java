package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class MasterImage implements ValueObject<MasterImage> {
    private String value;
    public MasterImage(String value){
        value=value;
        if(!StringUtils.ishttp(value)){
            throw new IllegalArgumentException("http地址无效");
        }
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(MasterImage other) {
        return false;
    }
}
