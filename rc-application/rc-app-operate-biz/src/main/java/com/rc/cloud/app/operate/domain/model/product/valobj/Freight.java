package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

import java.math.BigDecimal;

/**
 * @ClassName: FreeShipping
 * @Author: liandy
 * @Date: 2023/6/26 13:39
 * @Description: TODO
 */
public class Freight implements ValueObject<Freight> {
    private String type;
    private String templateId;
    private BigDecimal price;

    @Override
    public boolean sameValueAs(Freight other) {
        return false;
    }
}
