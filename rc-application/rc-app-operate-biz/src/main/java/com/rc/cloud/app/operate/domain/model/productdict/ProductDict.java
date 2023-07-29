package com.rc.cloud.app.operate.domain.model.productdict;


import com.rc.cloud.app.operate.domain.common.ProductDictKeyEnum;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductDictId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;

import java.util.Objects;

public class ProductDict extends  Entity {

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

    /**
     * key 限制
     * @param key
     */
    public void setKey(String key) {
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("");
        }
        if(isNotInDictPool(key)){
            throw new IllegalArgumentException("");
        }
        this.key = key;
    }

    private boolean isNotInDictPool(String key){
        boolean isNotFind=true;
        for (ProductDictKeyEnum value : ProductDictKeyEnum.values()) {
            if(value.name.equals(key)){
                isNotFind=false;
                break;
            }
        }
        return isNotFind;
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

    @Override
    public AbstractId getId() {
        return null;
    }
}
