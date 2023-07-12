package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {

    ProductId nextId();
    String nextProductImageId();
    String nextProductAttributeId();

    List<ProductImage> getProductImageByProductId(ProductId productId);

    int removeProductImageByProductId(ProductId productId);

    int insertProductImage(ProductImage productImage);

    int batchSaveProductImage(List<ProductImage> productImageList);

    int removeProductAttributeByProductId(ProductId productId);

    int insertProductAttribute(ProductAttribute productAttribute);

    int insertProduct(Product product);

    int updateProduct(Product product);

    Product findById(ProductId productId);

    boolean exist(ProductId productId);

    PageResult<Product> getProductPageList(ProductListQueryDTO query);


}
