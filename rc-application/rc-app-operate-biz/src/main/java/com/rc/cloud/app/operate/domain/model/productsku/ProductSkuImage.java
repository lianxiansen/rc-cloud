package com.rc.cloud.app.operate.domain.model.productsku;


import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.common.core.domain.AbstractId;
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

    private ProductSkuId productSkuId;
    private Url url;
    private Sort sort;

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

    public ProductSkuId getProductSkuId() {
        return productSkuId;
    }

    public ProductSkuImage(ProductSkuId productSkuId, Url url, Sort sort) {
        this.productSkuId = productSkuId;
        this.url = url;
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSkuImage that = (ProductSkuImage) o;
        return Objects.equals(productSkuId, that.productSkuId) && Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getSort(), that.getSort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productSkuId, getUrl(), getSort());
    }

    @Override
    public AbstractId getId() {
        return null;
    }
}
