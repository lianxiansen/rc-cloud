package com.rc.cloud.app.operate.domain.productsku;

import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.productsku.valobj.ProductSkuId;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId);

}
