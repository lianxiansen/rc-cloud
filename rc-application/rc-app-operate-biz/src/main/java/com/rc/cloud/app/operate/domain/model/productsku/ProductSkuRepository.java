package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;

import java.util.List;

public interface ProductSkuRepository {

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    int batchSaveProductSku(List<ProductSku> productSkuList);

    int updateProductSku(ProductSku productSku);

    int insertProductSku(ProductSku productSku);


    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);

    void removeProductSku(ProductSkuId productSkuId);

}
