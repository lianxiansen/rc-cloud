package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributeDO;
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
    void insertProductEntity(Product productEntity);
    void updateProductEntity(Product productEntity);
    Product findById(ProductId productId);

    boolean exist(ProductId productId);

    PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO);

    List<ProductDictEntity> getProductDictByProductId(ProductId productId);

    List<ProductImageEntity> getProductImageByProductId(ProductId productId);

    boolean existsByProductCategoryId(ProductCategoryId productCategoryId);

    void removeProductImageEntityByProductId(String productId);

    void removeProductDictEntityByProductId(String productId);

    void removeProductAttributeEntityByProductId(String productId);

    void updateProductImageEntity( ProductImageDO productImageDO);

    void updateProductDictEntity(ProductDictDO productDictDO);

    void updateProductAttributeEntity(ProductAttributeDO productAttributeDO);
}
