package com.rc.cloud.app.operate.domain.model.productimage;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;

import java.util.Objects;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImage extends  Entity {
    //TODO chenjx:加唯一标识
    private ProductImageId productImageId;
    private ProductId productId;

    private TenantId tenantId;

    private Url url;
    private Sort sort;
    private ProductImageTypeEnum type;

    public ProductImage( ProductImageId productImageId,ProductId productId, TenantId tenantId,Url url, Sort sort, ProductImageTypeEnum type) {
        this.productImageId = productImageId;
        this.productId = productId;
        this.tenantId=tenantId;
        this.url = url;
        this.sort = sort;
        this.type = type;
    }


    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
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
    public AbstractId getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getSort(), that.getSort()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getSort(), getType());
    }
}
