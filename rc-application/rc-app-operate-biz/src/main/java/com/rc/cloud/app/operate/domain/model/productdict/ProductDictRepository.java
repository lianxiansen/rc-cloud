package com.rc.cloud.app.operate.domain.model.productdict;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;

import java.util.List;

public interface ProductDictRepository
{
    List<ProductDict> getProductDictByProductId(ProductId productId);

    int removeProductDictByProductId(String productId);

}
