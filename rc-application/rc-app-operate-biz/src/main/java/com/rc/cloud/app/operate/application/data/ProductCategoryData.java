package com.rc.cloud.app.operate.application.data;

import com.rc.cloud.app.operate.domain.category.ProductCategoryEntity;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ProductCategoryData
 * @Author: liandy
 * @Date: 2023/6/23 14:32
 * @Description: TODO
 */
@Data
public class ProductCategoryData {
    private Date createTime;
    public static ProductCategoryData from(ProductCategoryEntity productCategoryEntry){
        return new ProductCategoryData();
    }
}
