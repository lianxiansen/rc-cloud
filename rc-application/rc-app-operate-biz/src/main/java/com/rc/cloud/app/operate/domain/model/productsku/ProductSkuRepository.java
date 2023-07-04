package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    void saveProductSku(ProductSku productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);


}
