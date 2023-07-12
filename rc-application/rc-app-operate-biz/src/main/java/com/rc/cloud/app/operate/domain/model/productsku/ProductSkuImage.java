package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.common.Entity;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductSkuImage extends Entity {

    private String id;
    private String url;
    private int sort;

    public ProductSkuImage(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public ProductSkuImage setUrl(String url) {
        this.assertArgumentNotNull(url, "url must not be null");
        this.url =url;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public ProductSkuImage setSort(int sort) {
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
