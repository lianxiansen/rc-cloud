package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuImageConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;

import java.util.List;

public interface ProductSkuRepository {


    ProductSkuId nextId();

    String nextProductSkuImageId();
    String nextProductSkuAttributeId();


    int removeProductSkuImageByProductSkuId(ProductSkuId productSkuId);

    int insertProductSkuImage(ProductSkuImage productSkuImage);

    int removeProductSkuAttributeByProductSkuId(ProductSkuId productSkuId);

    int insertProductSkuAttribute(ProductSkuAttribute productSkuAttribute);

    List<ProductSku> getProductSkuListByProductId(ProductId productId);

    int insertProductSku(ProductSku productSkuEntity);

    int updateProductSku(ProductSku productSkuEntity);

    boolean exist(ProductSkuId productSkuId);

    ProductSku findById(ProductSkuId productSkuId);



}
