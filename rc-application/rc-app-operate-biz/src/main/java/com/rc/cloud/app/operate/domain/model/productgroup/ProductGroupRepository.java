package com.rc.cloud.app.operate.domain.model.productgroup;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;

import java.util.List;

public interface ProductGroupRepository {
    boolean save(ProductGroup ProductGroupEntity);

    ProductGroup findById(ProductGroupId ProductGroupId);

    boolean removeById(ProductGroupId ProductGroupId);

    boolean itemExist(ProductGroupId productGroupId, ProductId productId);

    List<ProductGroup> selectList(ProductId productId);
}
