package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import com.rc.cloud.common.mybatis.core.dataobject.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("product_image")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductImagePO extends TenantBaseDO {


    private static final long serialVersionUID = 23434L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    @TableField("product_id")
    private String productId;


    @TableField("url")
    private String url;

    /**
     * 是否是默认
     */
    //@TableField("default_flag")
    //private Boolean defaultFlag;


    @TableField("sort")
    private Integer sort;

    @TableField("image_type")
    private Integer imageType;

}
