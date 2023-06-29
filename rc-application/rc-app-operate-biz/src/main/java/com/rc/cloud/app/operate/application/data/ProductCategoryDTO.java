package com.rc.cloud.app.operate.application.data;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @ClassName: ProductCategoryDTO
 * @Author: liandy
 * @Date: 2023/6/28 15:00
 * @Description: TODO
 */
@Data
public class ProductCategoryDTO {
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
