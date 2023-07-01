package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

/**
 * @ClassName: ProductCategoryDTO
 * @Author: liandy
 * @Date: 2023/6/28 15:00
 * @Description: TODO
 */
@Data
public class ProductCategoryUpdateRequest {
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


}
