package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Parent extends ValueObject{
    private String id;
    public Parent(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Parent typedObject = (Parent) other;
            return this.id.equals(typedObject.id);
        }
        return false;
    }
}
