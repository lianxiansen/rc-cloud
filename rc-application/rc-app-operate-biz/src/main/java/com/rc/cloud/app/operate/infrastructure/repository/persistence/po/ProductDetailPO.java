package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品详情
 * @Author taotianhong
 * @Date 2021/3/25
 * @Description:
 */
@TableName("product_detail")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetailPO extends BaseDO {


    private static final long serialVersionUID = 34322L;


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 品牌ID
     */
    @TableField("product_id")
    private String productId;


    @TableField("detail")
    private String detail;


    @TableField("install_video_url")
    private String installVideoUrl;

    @TableField("install_video_img")
    private String installVideoImg;

    @TableField("install_detail")
    private String installDetail;
}
