package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

public interface ProductGroupDomainService {
    ProductGroup create(String name, TenantId tenantId, ProductId productId);

    ProductGroupItem createItem(ProductGroupId productGroupId, ProductId productId);

    ProductGroup findById(ProductGroupId productGroupId);

    boolean release(ProductGroupId productGroupId);
}
