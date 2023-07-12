package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Parent implements ValueObject<Parent> {
    private String id;
    public Parent(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    @Override
    public boolean sameValueAs(Parent other) {
        return false;
    }
}
