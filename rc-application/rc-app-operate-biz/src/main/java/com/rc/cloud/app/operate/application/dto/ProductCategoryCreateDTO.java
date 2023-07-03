package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

/**
 * @ClassName: ProductCategoryCreateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: TODO
 */
@Data
public class ProductCategoryCreateDTO {
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
