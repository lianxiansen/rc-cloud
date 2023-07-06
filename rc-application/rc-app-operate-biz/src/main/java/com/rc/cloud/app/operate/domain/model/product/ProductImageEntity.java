package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Image;
import com.rc.cloud.app.operate.domain.model.product.valobj.ImageDefault;
import com.rc.cloud.app.operate.domain.model.product.valobj.ImageSort;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImageEntity extends Entity {

    private ProductImageId id;

    private String url;
    private int sort;
    private boolean defaultFlag;

    public String getUrl() {
        return url;
    }

    public ProductImageEntity setUrl(String url) {
        this.assertArgumentNotNull(url, "url must not be null");
        if(!StringUtils.ishttp(url)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.url =url;
        this.defaultFlag=false;//暂时没有用
        return this;
    }

    public int getSort() {
        return sort;
    }

    public ProductImageEntity setSort(int sort) {
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    public ProductImageEntity setDefaultFlag(boolean defaultFlag) {

        this.assertArgumentNotNull(defaultFlag, "defaultFlag must not be null");

        return this;
    }
}
