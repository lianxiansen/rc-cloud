package com.rc.cloud.app.product.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_category")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends BaseDO {


    private static final long serialVersionUID = 2123435L;
    @TableField("id")
    private String Id;
    @TableField("tenant_id")
    private Long tenantId;

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
    private Integer parentId;

    @TableField("layer")
    private Integer layer;

    @TableField("enabled_flag")
    private Boolean enabledFlag;

    @TableField("sort_id")
    private Integer sortId;


}
