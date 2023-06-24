package com.rc.cloud.app.mall.domain.product.entity;

import com.rc.cloud.app.mall.domain.common.Entity;
import com.rc.cloud.app.mall.domain.product.valobj.*;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImageEntry extends Entity {

    private ProductImageId productImageId;
    private ProductId productId;
    private ProductImage productImage;

    private ProductImageSort productImageSort;
    private ProductImageDefault productImageDefault;
    public ProductImageEntry(ProductImageId productImageId,ProductId productId,ProductImage productImage){

    }

    public void setProductImageSort(ProductImageSort productImageSort){

    }
    public void setProductImageDefault(ProductImageDefault productImageDefault){

    }

}
