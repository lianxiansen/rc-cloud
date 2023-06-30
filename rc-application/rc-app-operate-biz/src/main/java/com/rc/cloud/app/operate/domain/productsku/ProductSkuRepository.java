package com.rc.cloud.app.operate.domain.productsku;

import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;

import java.util.List;

public interface ProductSkuRepository {

    List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId);

}
