package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
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

    int removeProductImageByUrlAndSortId(String url ,int sortId);



    int batchSaveProductImage(List<ProductImage> productImageList,String productId,String tenantId);

    ProductAttribute getProductAttributeByProductId(ProductId productId);

    int removeProductAttributeByProductId(ProductId productId);

    int insertProductAttribute(Product product);

    int insertProduct(Product product);

    int updateProduct(Product product);

    Product findById(ProductId productId);

    boolean exist(ProductId productId);

    PageResult<Product> getProductPageList(ProductListQueryDTO query);


    List<ProductDict> getProductDictByProductId(ProductId productId);

    boolean existsByBrandId(BrandId brandId);

    void insertProductEntity(Product product);

    void updateProductEntity(Product product);

    boolean existsByProductCategoryId(ProductCategoryId productCategoryId);
}
