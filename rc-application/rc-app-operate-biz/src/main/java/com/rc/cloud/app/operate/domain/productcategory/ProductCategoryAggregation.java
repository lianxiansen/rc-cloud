package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品分类
 */
public class ProductCategoryAggregation extends Entity {
    private ProductCategoryId id;
    private TenantId tenantId;
    private Name name;
    private Icon icon;
    private Page page;
    private ProductCategoryId parentId;
    private Layer layer;
    private Enable enable;
    private Sort sort;

    public ProductCategoryAggregation(ProductCategoryId id, TenantId tenantId, Name name) {
        setId(id);
        setTenantId(tenantId);
        setName(name);
    }

    public void setId(ProductCategoryId id) {
        this.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public void setTenantId(TenantId tenantId) {
        this.assertArgumentNotNull(tenantId, "tenantId must not be null");
        this.tenantId = tenantId;
    }

    public void setName(Name name) {
        this.assertArgumentNotNull(name, "name must not be null");
        this.name = name;
    }

    public void setIcon(Icon icon) {
        this.assertArgumentNotNull(icon, "icon must not be null");
        this.icon = icon;
    }

    public void setPage(Page page) {
        this.assertArgumentNotNull(page, "page must not be null");
        this.page = page;
    }

    public void setParentId(ProductCategoryId parentId) {
        this.parentId = parentId;
    }

    public ProductCategoryId getParentId() {
        return this.parentId;
    }

    public void setLayer(Layer layer) {
        this.assertArgumentNotNull(layer, "layer must not be null");
        this.layer = layer;
    }

    public Layer getLayer() {
        return this.layer;
    }
}
