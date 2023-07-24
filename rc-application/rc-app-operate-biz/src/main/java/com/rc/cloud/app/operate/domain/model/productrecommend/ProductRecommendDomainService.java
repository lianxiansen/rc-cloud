package com.rc.cloud.app.operate.domain.model.productrecommend;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.List;

public interface ProductRecommendDomainService {
    ProductRecommend create(TenantId tenantId, ProductId productId, ProductId recommendProductId);

    ProductRecommend findById(ProductRecommendId productRecommendId);

    boolean release(ProductRecommendId productRecommendId);

    List<ProductRecommend> findListByProductId(ProductId productId);
}
