package com.rc.cloud.app.operate.domain.model.productsku;


import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.Objects;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductSkuImage extends Entity {

    private ProductSkuImageId id;
    private Url url;
    private Sort sort;
    public ProductSkuImage(ProductSkuImageId id) {
        this.id = id;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public ProductSkuImageId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSkuImage that = (ProductSkuImage) o;
        return getSort() == that.getSort() && Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getSort());
    }
}
