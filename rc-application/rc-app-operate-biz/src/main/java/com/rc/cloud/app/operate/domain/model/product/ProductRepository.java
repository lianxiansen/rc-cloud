package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {
    void saveProductEntry(ProductAggregation productEntry);
    ProductAggregation findById(ProductId productId);
    ProductId nextProductId();

    ProductImageId nextProductImageId();

    PageResult<ProductDO> getProductPageList(ProductListQueryDTO productListQueryDTO);

}
