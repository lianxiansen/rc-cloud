package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;

public interface ProductGroupDomainService {
    ProductGroup create( ProductGroup productGroup);

    ProductGroupItem createItem(ProductGroup productGroup,ProductGroupItem item);

    ProductGroup findById(ProductGroupId productGroupId);

    boolean release(ProductGroup productGroup);
}
