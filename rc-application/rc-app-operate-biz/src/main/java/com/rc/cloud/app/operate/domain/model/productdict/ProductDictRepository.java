package com.rc.cloud.app.operate.domain.model.productdict;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;

import java.util.List;

public interface ProductDictRepository
{
    String nextId();

    List<ProductDict> getProductDictByProductId(ProductId productId);

    int removeProductDictByProductId(String productId);

    void saveProductDict(List<ProductDict> productDictList);

}
