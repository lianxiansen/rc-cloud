package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.valobj.Image;
import com.rc.cloud.app.operate.domain.product.valobj.ImageDefault;
import com.rc.cloud.app.operate.domain.product.valobj.ImageSort;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImageEntity extends Entity {

    private ProductImageId productImageId;
    private ProductId productId;
    private Image productImage;

    private ImageSort productImageSort;
    private ImageDefault productImageDefault;
    public ProductImageEntity(ProductImageId productImageId, ProductId productId, Image productImage){

    }
    public void setProductImageId(ProductImageId productImageId){

    }

    public void setProductImageSort(ImageSort productImageSort){

    }
    public void setProductImageDefault(ImageDefault productImageDefault){

    }

}
