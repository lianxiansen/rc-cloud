package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_group_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductGroupItemPO extends BaseDO {

    private static final long serialVersionUID = 2343242L;
    @TableField("id")
    private String Id;

    @TableField("product_group_id")
    private String productGroupId;

    @TableField("product_id")
    private String productId;

}
