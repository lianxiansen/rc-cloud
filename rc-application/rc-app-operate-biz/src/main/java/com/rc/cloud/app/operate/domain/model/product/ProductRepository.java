package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @ClassName: ProductRepository
 * @Author: liandy
 * @Date: 2023/6/23 15:59
 * @Description: TODO
 */
public interface ProductRepository {


    List<ProductImage> getProductImageByProductId(ProductId productId);

    int removeProductImageByProductId(ProductId productId);

    int removeProductImageByUrlAndSortAndType(String url ,int sort , int type);


    int batchSaveProductImage(List<ProductImage> productImageList,String productId,String tenantId);

    ProductAttribute getProductAttributeByProductId(ProductId productId);

    int removeProductAttributeByProductId(ProductId productId);

    int insertProductAttribute(Product product);

    int insertProduct(Product product);

    int updateProduct(Product product);

    Product findById(ProductId productId);

    boolean exist(ProductId productId);

    PageResult<Product> getProductPageList(ProductListQueryDTO query);


    boolean existsByBrandId(BrandId brandId);

    boolean existsByProductCategoryId(ProductCategoryId productCategoryId);


}
