package com.rc.cloud.app.operate.domain.model.productsku;


import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductSkuImage extends Entity {

    private ProductSkuImageId id;
    private String url;
    private int sort;
    public ProductSkuImage(ProductSkuImageId id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    public ProductSkuImage setUrl(String url) {
        AssertUtils.assertArgumentNotNull(url, "url must not be null");
        this.url =url;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public ProductSkuImage setSort(int sort) {
        AssertUtils.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    @Override
    public ProductSkuImageId getId() {
        return id;
    }


}
