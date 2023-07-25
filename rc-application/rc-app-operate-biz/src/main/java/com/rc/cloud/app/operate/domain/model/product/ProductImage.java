package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.Objects;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImage extends  Entity {

    private ProductImageId id;
    private Url url;
    private Sort sort;
    private ProductImageTypeEnum type;

    public ProductImage(ProductImageId id, Url url, Sort sort, ProductImageTypeEnum type) {
        this.id = id;
        this.url = url;
        this.sort = sort;
        this.type = type;
    }

    @Override
    public ProductImageId getId() {
        return id;
    }

    public void setId(ProductImageId id) {
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

    public ProductImageTypeEnum getType() {
        return type;
    }

    public void setType(ProductImageTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getSort(), that.getSort()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getSort(), getType());
    }
}
