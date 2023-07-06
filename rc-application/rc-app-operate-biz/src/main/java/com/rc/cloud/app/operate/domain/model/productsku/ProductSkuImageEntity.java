package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductSkuImageEntity extends Entity {

    private ProductImageId id;

    private String url;
    private int sort;

    public String getUrl() {
        return url;
    }

    public ProductSkuImageEntity setUrl(String url) {
        this.assertArgumentNotNull(url, "url must not be null");
        this.url =url;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public ProductSkuImageEntity setSort(int sort) {
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }
}
