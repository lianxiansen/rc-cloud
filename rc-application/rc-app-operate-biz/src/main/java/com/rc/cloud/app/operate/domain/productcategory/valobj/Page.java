package com.rc.cloud.app.operate.domain.productcategory.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Page extends ValueObject {
    /**
     * 商品分类页面图片URL
     */
    private String categoryImage;
    /**
     * 商品列表页面图片URL
     */
    private String listImage;
    public Page(){
        setCategoryImage("");
        setListImage("");
    }

    public Page(String categoryImage, String listImage) {
        setCategoryImage(categoryImage);
        setListImage(listImage);
    }

    private void setCategoryImage(String categoryImage) {
        if (StringUtils.isNotEmpty(categoryImage) && !StringUtils.ishttp(categoryImage)) {
            throw new IllegalArgumentException("http地址无效");
        }
        this.categoryImage = categoryImage;
    }

    private void setListImage(String listImage) {
        if (StringUtils.isNotEmpty(listImage) && !StringUtils.ishttp(listImage)) {
            throw new IllegalArgumentException("http地址无效");
        }
        this.listImage = listImage;
    }

    public String getCategoryImage(){
        return this.categoryImage;
    }

    public String getListImage() {
        return this.listImage;
    }
}
