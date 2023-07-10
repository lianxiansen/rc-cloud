package com.rc.cloud.app.operate.infrastructure.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductGroupDO extends BaseDO {

    private static final long serialVersionUID = 2343242L;
    @TableField("id")
    private String Id;

    @TableField("name")
    private String name;


}
