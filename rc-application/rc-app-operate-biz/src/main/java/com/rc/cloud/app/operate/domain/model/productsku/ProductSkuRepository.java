package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;

import java.util.List;

public interface ProductSkuRepository {




    int removeProductSkuImageByProductSkuId(ProductSkuId productSkuId);

    int batchSaveProductSkuImage(ProductSku productSku);

    int removeProductSkuAttributeByProductSkuId(ProductSkuId productSkuId);

    int insertProductSkuAttribute(ProductSku productSku);

    List<ProductSku> getProductSkuListByProductId(ProductId productId);


    int batchSaveProductSku(List<ProductSku> productSkuList);

    int updateProductSku(ProductSku productSku);

    int insertProductSku(ProductSku productSku);

    int removeProductSku(ProductSkuId productSkuId);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);


    List<ProductSkuImage> getProductSkuImageByProductSkuId(ProductSkuId productSkuId);


    ProductSkuAttribute getProductSkuAttributeByProductSkuId(ProductSkuId productSkuId);


}
