package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImage {

    private String id;

    private String url;
    private int sort;
    //private boolean defaultFlag;

    public String getUrl() {
        return url;
    }

    public ProductImage(String id){
        this.id= id;
    }


    public ProductImage setUrl(String url) {
        AssertUtils.assertArgumentNotNull(url, "url must not be null");
        if(!StringUtils.ishttp(url)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.url =url;
        //this.defaultFlag=false;//暂时没有用
        return this;
    }

    public int getSort() {
        return sort;
    }

    public ProductImage setSort(int sort) {
        AssertUtils.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    public ProductImage setDefaultFlag(boolean defaultFlag) {

        AssertUtils.assertArgumentNotNull(defaultFlag, "defaultFlag must not be null");

        return this;
    }

    public String getId() {
        return id;
    }

}
