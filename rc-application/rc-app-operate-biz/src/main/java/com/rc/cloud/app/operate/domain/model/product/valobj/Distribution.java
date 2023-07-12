package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: Distribution
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: TODO
 */
public class Distribution implements ValueObject<Distribution> {
    private boolean flag;

    @Override
    public boolean sameValueAs(Distribution other) {
        return false;
    }
}
