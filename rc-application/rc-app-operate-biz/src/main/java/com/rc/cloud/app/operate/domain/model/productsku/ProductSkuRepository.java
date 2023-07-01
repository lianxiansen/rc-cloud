package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId);

    void saveProductSku(ProductSkuEntity productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSkuEntity findById(ProductSkuId productSkuId);


}
