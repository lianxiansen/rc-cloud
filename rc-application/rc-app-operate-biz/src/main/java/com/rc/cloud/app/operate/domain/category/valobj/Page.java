package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Page extends AssertionConcern {
    /**
     * 商品分类页面图片URL
     */
    private String categoryImage;
    /**
     * 商品列表页面图片URL
     */
    private String listImage;
    public Page(String categoryImage,String listImage){
        this.categoryImage=categoryImage;
        this.listImage = listImage;
    }
}
