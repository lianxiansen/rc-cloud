package com.rc.cloud.app.operate.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: ProductCategoryCreateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class ProductCategoryCreateDTO {
    @NotNull(message = "产品分类名称不能为空")
    private String name;

    private String englishName;

    private String icon;

    private String productCategoryPageImage;

    private String productListPageImage;

    private String parentId;

    private Boolean enabledFlag;

    private Integer sortId;


}
