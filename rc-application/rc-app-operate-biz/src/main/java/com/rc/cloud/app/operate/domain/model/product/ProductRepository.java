package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {
    void saveProductEntry(Product productEntry);
    Product findById(ProductId productId);

    boolean exist(ProductId productId);

    ProductId nextProductId();

    ProductImageId nextProductImageId();

    PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO);

    List<ProductDictEntity> getProductDictByProductId(ProductId productId);

    List<ProductImageEntity> getProductImageByProductId(ProductId productId);

    boolean existsByProductCategoryId(ProductCategoryId productCategoryId);

}
