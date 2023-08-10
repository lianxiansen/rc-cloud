package com.rc.cloud.app.operate.domain.model.productgroup;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupItemPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupPO;

import java.util.List;

public interface ProductGroupRepository {
    boolean save(ProductGroup productGroup);

    boolean saveProductGroupPO(ProductGroupPO po);

    boolean saveProductGroupItemPO(ProductGroupItemPO po);

    boolean exists(ProductGroupId productGroupId);

    ProductGroup findById(ProductGroupId productGroupId);

    boolean remove(ProductGroup productGroup);

    boolean removeById(ProductGroupId productGroupId);

    boolean itemExist(ProductGroupId productGroupId, ProductId productId);

    List<ProductGroup> findAll(ProductId productId);
}
