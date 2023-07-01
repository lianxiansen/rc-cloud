package com.rc.cloud.app.operate.domain.model.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class Name extends ValueObject {
    /**
     * 分类名（中文名）
     */
    private String chName;
    /**
     * 分类名（英文名）
     */
    private String enName;

    public Name(String chName){
        setChName(chName);
    }
    public Name(String ChName,String EnglishName){
        setChName(chName);
        setEnName(enName);
    }

    private void setChName(String chName){
        this.assertArgumentNotNull(chName,"chName must not be null");
        this.chName =chName;
    }
    private void setEnName(String enName){
        this.enName =enName;
    }

    public String getEnName() {
        return this.enName;
    }

    public String getChName() {
        return this.chName;
    }
}
