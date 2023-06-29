package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class Name extends AssertionConcern {
    /**
     * 分类名（中文名）
     */
    private String chName;
    /**
     * 分类名（英文名）
     */
    private String enName;

    public Name(String ChName){
        setChName(chName);
    }
    public Name(String ChName,String EnglishName){
        setChName(chName);
        setEnName(enName);
    }

    public void setChName(String chName){
        this.assertArgumentNotNull(chName,"chName must not be null");
        this.chName =chName;
    }
    public void setEnName(String enName){
        this.enName =enName;
    }
}
