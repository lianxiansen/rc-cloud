package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: Refund
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 抢购
 */
public class Seckill extends Entity {
    private boolean flag;


    @Override
    public AbstractId getId() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
