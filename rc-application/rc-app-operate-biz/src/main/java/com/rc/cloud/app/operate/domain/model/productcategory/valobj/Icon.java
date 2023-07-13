package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Icon extends ValueObject {
    private String pictureUrl;
    public Icon(){
        setPictureUrl("");
    }
    public Icon(String pictureUrl){
        setPictureUrl(pictureUrl);
    }
    private void setPictureUrl(String pictureUrl){
        if(StringUtils.isNotEmpty(pictureUrl)&&!StringUtils.ishttp(pictureUrl)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.pictureUrl=pictureUrl;
    }

    public String getPictureUrl(){
        return pictureUrl;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            Icon typedObject = (Icon) other;
        }
        return false;
    }
}
