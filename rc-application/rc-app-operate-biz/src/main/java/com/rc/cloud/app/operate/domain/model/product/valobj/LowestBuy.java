package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: LowestBuy
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class LowestBuy implements ValueObject<LowestBuy> {
    private int num;

    @Override
    public boolean sameValueAs(LowestBuy other) {
        return false;
    }
}
