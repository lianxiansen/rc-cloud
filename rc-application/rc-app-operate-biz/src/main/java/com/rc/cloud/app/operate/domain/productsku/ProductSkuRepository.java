package com.rc.cloud.app.operate.domain.productsku;

import com.rc.cloud.app.operate.domain.product.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId);

}
