package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Name implements ValueObject<Name> {
    private String value;
    public Name(String value){
        this.setValue(value);
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("商品名称不为空");
        }

    }

    @Override
    public boolean sameValueAs(Name other) {
        return false;
    }
}
