package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.common.Entity;
import com.rc.cloud.app.product.domain.product.valobj.*;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImageEntity extends Entity {

    private ProductImageId productImageId;
    private ProductId productId;
    private ProductImage productImage;

    private ProductImageSort productImageSort;
    private ProductImageDefault productImageDefault;
    public ProductImageEntity(ProductImageId productImageId, ProductId productId, ProductImage productImage){

    }
    public void setProductImageId(ProductImageId productImageId){

    }

    public void setProductImageSort(ProductImageSort productImageSort){

    }
    public void setProductImageDefault(ProductImageDefault productImageDefault){

    }

}