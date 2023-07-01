package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class ChName extends ValueObject {
    /**
     * 分类名（中文名）
     */
    private String value;
    public ChName(String ChName){
        setValue(value);
    }

    private void setValue(String value){
        this.assertArgumentNotEmpty(value,"chName must not be empty");
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
