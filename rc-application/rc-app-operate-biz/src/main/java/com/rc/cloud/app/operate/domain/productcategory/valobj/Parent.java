package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Parent extends ValueObject {
    private String id;
    public Parent(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}
