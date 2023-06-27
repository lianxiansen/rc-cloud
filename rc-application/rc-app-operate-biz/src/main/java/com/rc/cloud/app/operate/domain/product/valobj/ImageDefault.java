package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ImageDefault extends ValueObject {
    private int value;
    public ImageDefault(int value){
        this.value=value;
    }
    public ImageDefault(){
        this.value=0;
    }
}
