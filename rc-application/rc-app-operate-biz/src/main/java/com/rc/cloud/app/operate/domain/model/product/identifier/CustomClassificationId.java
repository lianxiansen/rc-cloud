package com.rc.cloud.app.operate.domain.model.product.identifier;

import com.rc.cloud.app.operate.domain.common.AbstractId;
import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: CustomClassification
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 自定义分类
 */
public class CustomClassificationId  extends AbstractId {

    public CustomClassificationId(String id) {
        super(id);
    }

    protected CustomClassificationId() {
        super();
    }


}
