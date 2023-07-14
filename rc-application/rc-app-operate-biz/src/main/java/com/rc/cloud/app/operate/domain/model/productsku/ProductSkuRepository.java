package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    String nextProductSkuImageId();
    String nextProductSkuAttributeId();


    int removeProductSkuImageByProductSkuId(ProductSkuId productSkuId);

    int batchSaveProductSkuImage(ProductSku productSku);

    int removeProductSkuAttributeByProductSkuId(ProductSkuId productSkuId);

    int insertProductSkuAttribute(ProductSku productSku);

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    int insertProductSku(ProductSku productSku);

    int batchSaveProductSku(List<ProductSku> productSkuList);

    int updateProductSku(ProductSku productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);



}
