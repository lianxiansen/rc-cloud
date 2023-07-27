package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-02-19
 */
@TableName("product")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPO extends BaseDO {

    private static final long serialVersionUID = 14345345L;
    private String id;
    /**
     * 租户id
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 品牌ID
     */
    @TableField("brand_id")
    private String brandId;

    /**
     * 商品名
     */
    @TableField("name")
    private String name;

    /**
     * 商品列表图片
     */
    @TableField("list_image")
    private String listImage;

    @TableField("remark")
    private String remark;

    @TableField("tag")
    private String tag;

    /**
     * 商品类型，0：普通商品
     */
    @TableField("product_type")
    private Integer productType;

    /**
     * 货号
     */
    @TableField("spu_code")
    private String spuCode;

    /**
     * 商品来源，0：ffcat
     */
    @TableField("product_origin")
    private Integer productOrigin;

    /**
     * 外部id
     */
    @TableField("out_id")
    private String outId;

    /**
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    @TableField("onshelf_status")
    private Integer onshelfStatus;

    @TableField("first_category")
    private String firstCategory;

    @TableField("second_category")
    private String secondCategory;

    @TableField("third_category")
    private String thirdCategory;

    @TableField("video_url")
    private String videoUrl;

    @TableField("video_img")
    private String videoImg;

    @TableField("install_video_url")
    private String installVideoUrl;

    @TableField("install_video_img")
    private String installVideoImg;

    @TableField("install_detail")
    private String installDetail;

    @TableField("free_shipping_flag")
    private Boolean freeShippingFlag;

    /**
     * 运费类型，0统一运费，1运费模板
     */
    @TableField("freight_type")
    private Integer freightType;

    /**
     * 商品使用运费模板
     */
    @TableField("freight_template_id")
    private Long freightTemplateId;

    /**
     * 运费价格
     */
    @TableField("freight_price")
    private BigDecimal freightPrice;

    /**
     * 最低起购量
     */
    @TableField("packing_lowest_buy_flag")
    private Boolean packingLowestBuyFlag;

    /**
     * 推广佣金比例
     */
    @TableField("popularization_amount_rate")
    private BigDecimal popularizationAmountRate;

    /**
     * 可获得积分数量
     */
    @TableField("get_integral")
    private BigDecimal getIntegral;

    /**
     * 是否参与分销 0否，1是
     */
    @TableField("distribution_flag")
    private Boolean distributionFlag;

    /**
     * 是否可以退款 0无法退款，1可以退款
     */
    @TableField("refund_flag")
    private Boolean refundFlag;

    /**
     * 是否是抢购活动 0否，1是
     */
    @TableField("seckill_flag")
    private Boolean seckillFlag;

    @TableField("seckill_name")
    private String seckillName;

    @TableField("seckill_master_image")
    private String seckillMasterImage;

    @TableField("seckill_begin_time")
    private LocalDateTime seckillBeginTime;

    @TableField("seckill_end_time")
    private LocalDateTime seckillEndTime;

    /**
     * 是否新品 0否，1是
     */
    @TableField("new_flag")
    private Boolean newFlag;

    @TableField("explosives_flag")
    private Boolean explosivesFlag;

    @TableField("explosives_image")
    private String explosivesImage;

    @TableField("public_flag")
    private Boolean publicFlag;

    @TableField("recommend_flag")
    private Boolean recommendFlag;

    @TableField("sort")
    private Integer sort;

    @TableField("custom_classification_id")
    private String customClassificationId;
}
