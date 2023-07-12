package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: FreeShipping
 * @Author: liandy
 * @Date: 2023/6/26 13:39
 * @Description: TODO
 */
public class FreeShipping implements ValueObject<FreeShipping> {
    private String value;

    @Override
    public boolean sameValueAs(FreeShipping other) {
        return false;
    }
}
