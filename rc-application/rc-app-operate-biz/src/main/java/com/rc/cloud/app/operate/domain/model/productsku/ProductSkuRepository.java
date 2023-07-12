package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;

import java.util.List;

public interface ProductSkuRepository {

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    void insertProductSku(ProductSku productSkuEntity);

    void updateProductSku(ProductSku productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);


    public void updateProductSkuImageEntity( ProductSkuImagePO productSkuImagePO);

    public void updateProductSkuAttributeEntity(ProductSkuAttributePO productSkuAttributePO);

}
