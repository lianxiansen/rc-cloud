package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Image;
import com.rc.cloud.app.operate.domain.model.product.valobj.ImageDefault;
import com.rc.cloud.app.operate.domain.model.product.valobj.ImageSort;

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
        this.url =url;
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

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public ProductImageEntity setDefaultFlag(boolean defaultFlag) {

        this.assertArgumentNotNull(defaultFlag, "defaultFlag must not be null");
        this.defaultFlag = defaultFlag;
        return this;
    }
}
