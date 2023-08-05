package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class Tag extends ValueObject{
    private String value;
    public Tag(String value){
        //检验标签是否合法
       setValue(value);
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(StringUtils.isNotEmpty(value)){
            if(value.length()>1000){
                throw new IllegalArgumentException("The tag length cannot be greater than 1000");
            }
            String[] arr = value.split(",");
            if(arr==null || arr.length==0){
                throw new ServiceException(ProductErrorCodeConstants.PRODUCT_TAG_ERROR);
            }
            for (String s : arr) {
                if(StringUtils.isEmpty(s)){
                    throw new ServiceException(ProductErrorCodeConstants.PRODUCT_TAG_ERROR);
                }
            }
        }
        this.value=value;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
