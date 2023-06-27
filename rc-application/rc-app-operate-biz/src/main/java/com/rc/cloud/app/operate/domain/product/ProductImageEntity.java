package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.product.valobj.*;

/**
 * @ClassName: ProductImageEntry
 * @Author: liandy
 * @Date: 2023/6/23 17:02
 * @Description: TODO
 */
public class ProductImageEntity extends Entity {

    private ImageId productImageId;
    private Id productId;
    private Image productImage;

    private ImageSort productImageSort;
    private ImageDefault productImageDefault;
    public ProductImageEntity(ImageId productImageId, Id productId, Image productImage){

    }
    public void setProductImageId(ImageId productImageId){

    }

    public void setProductImageSort(ImageSort productImageSort){

    }
    public void setProductImageDefault(ImageDefault productImageDefault){

    }

}
