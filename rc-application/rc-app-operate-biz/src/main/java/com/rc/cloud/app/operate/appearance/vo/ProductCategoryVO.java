package com.rc.cloud.app.operate.appearance.vo;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import lombok.Data;

/**
 * @ClassName: ProductCategoryUpdateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: TODO
 */
@Data
public class ProductCategoryVO {
    private String Id;
    private String tenantId;

    private String name;

    private String englishName;

    private String icon;

    private String productCategoryPageImage;

    private String productListPageImage;

    private String parentId;

    private Boolean enabledFlag;

    private Integer sortId;

    public static ProductCategoryVO from(ProductCategoryBO productCategory){
        return new ProductCategoryVO();
    }

}
