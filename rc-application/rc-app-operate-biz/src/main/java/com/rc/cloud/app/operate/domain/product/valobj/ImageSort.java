package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class ImageSort extends ValueObject {
    private int value;
    public ImageSort(int value){
        this.value=value;
    }
}