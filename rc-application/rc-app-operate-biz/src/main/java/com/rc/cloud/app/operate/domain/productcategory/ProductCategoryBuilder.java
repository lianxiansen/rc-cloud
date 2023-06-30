package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import lombok.Data;

/**
 * @ClassName: ProductCategoryBuilder
 * @Author: liandy
 * @Date: 2023/6/30 13:56
 * @Description: TODO
 */
@Data
public class ProductCategoryBuilder {
    private ProductCategoryId id;
    private TenantId tenantId;
    private Name name;
    private Icon icon;
    private Page page;
    private Layer layer;
    private ProductCategoryId parentId;
    private Enabled enabled;
    private Sort sort;
}
