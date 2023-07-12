package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

import java.math.BigDecimal;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Popularization implements ValueObject<Popularization> {
    /**
     * 比率
     */
    private BigDecimal amountRate;

    @Override
    public boolean sameValueAs(Popularization other) {
        return false;
    }
}
