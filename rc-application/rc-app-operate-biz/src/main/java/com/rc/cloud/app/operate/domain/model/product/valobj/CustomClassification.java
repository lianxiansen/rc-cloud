package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: CustomClassification
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 自定义分类
 */
public class CustomClassification extends ValueObject {

    private String id;
    public CustomClassification(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }
}
