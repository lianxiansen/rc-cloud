package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Tag implements ValueObject<Tag> {
    private String value;
    public Tag(String value){
        //检验标签是否合法
       setValue(value);
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(StringUtils.isNotEmpty(value) && value.length()>1000){
            throw new IllegalArgumentException(" The tag length cannot be greater than 1000");
        }

    }

    @Override
    public boolean sameValueAs(Tag other) {
        return false;
    }
}
