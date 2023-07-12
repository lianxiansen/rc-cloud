package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: Refund
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Refund implements ValueObject<Refund> {
    private boolean flag;

    @Override
    public boolean sameValueAs(Refund other) {
        return false;
    }
}
