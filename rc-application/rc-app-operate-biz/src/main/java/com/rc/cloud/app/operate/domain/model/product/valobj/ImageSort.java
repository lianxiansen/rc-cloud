package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ImageSort implements ValueObject<ImageSort> {
    private int value;
    public ImageSort(int value){
        this.value=value;
    }

    @Override
    public boolean sameValueAs(ImageSort other) {
        return false;
    }
}
