package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_category")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryPO extends BaseDO {


    private static final long serialVersionUID = 2123435L;
    private String id;
    @TableField("tenant_id")
    private String tenantId;

    @TableField("name")
    private String name;

    @TableField("english_name")
    private String englishName;

    @TableField("icon")
    private String icon;

    @TableField("product_category_page_image")
    private String productCategoryPageImage;

    @TableField("product_list_page_image")
    private String productListPageImage;

    @TableField("parent_id")
    private String parentId;

    @TableField("layer")
    private Integer layer;

    @TableField("enabled_flag")
    private Boolean enabledFlag;

    @TableField("sort")
    private Integer sort;


}
