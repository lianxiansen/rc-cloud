package com.rc.cloud.app.operate.domain.model.productdict;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductDictId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.Entity;

import java.util.Objects;

public class ProductDict {

    private ProductId productId;

    private TenantId tenantId;

    private String id;
    public ProductDict(String id) {
            this.id = id;
        }

    private String key;

    private String value;

    private int sort;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


    public String getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDict that = (ProductDict) o;
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getTenantId(), that.getTenantId()) && Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getTenantId(), getKey());
    }
}
