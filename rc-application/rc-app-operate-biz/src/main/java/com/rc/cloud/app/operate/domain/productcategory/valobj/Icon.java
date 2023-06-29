package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Icon extends AssertionConcern {
    private String picture;
    public Icon(String picture){
        setPicture(picture);
    }
    public void setPicture(String picture){
        this.assertArgumentNotNull(picture,"picture must not be null");
    }
}
