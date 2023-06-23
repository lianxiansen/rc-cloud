package com.rc.cloud.app.mall.application.data;

import com.rc.cloud.app.mall.domain.category.entity.ProductCategoryEntry;
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
    public static ProductCategoryData from(ProductCategoryEntry productCategoryEntry){
        return new ProductCategoryData();
    }
}
