package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.Entity;


public class ProductDetail extends Entity
{

    private ProductDetailId id;

    private TenantId tenantId;

    private ProductId productId;

    private String detail;

    public ProductDetail(ProductDetailId id,TenantId tenantId,ProductId productId, String detail) {
        this.id =id;
        this.tenantId=tenantId;
        this.productId = productId;
        this.detail = detail;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getDetail() {
        return detail;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public ProductDetailId getId() {
        return id;
    }

    public void setId(ProductDetailId id) {
        this.id = id;
    }
}
