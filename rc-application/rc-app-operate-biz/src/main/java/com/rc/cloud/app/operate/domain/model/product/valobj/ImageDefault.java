package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ImageDefault implements ValueObject<ImageDefault> {
    private int value;
    public ImageDefault(int value){
        this.value=value;
    }
    public ImageDefault(){
        this.value=0;
    }

    @Override
    public boolean sameValueAs(ImageDefault other) {
        return false;
    }
}
