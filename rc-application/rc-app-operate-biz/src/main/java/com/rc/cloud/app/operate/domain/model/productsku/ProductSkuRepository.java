package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributeDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImageDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    void insertProductSku(ProductSku productSkuEntity);

    void updateProductSku(ProductSku productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);


    public void updateProductSkuImageEntity( ProductSkuImageDO productSkuImageDO);

    public void updateProductSkuAttributeEntity(ProductSkuAttributeDO productSkuAttributeDO);

}
