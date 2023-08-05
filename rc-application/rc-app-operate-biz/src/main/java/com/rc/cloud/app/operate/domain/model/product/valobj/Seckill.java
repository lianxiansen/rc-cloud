package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
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
