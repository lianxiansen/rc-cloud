package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxun.qlive.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author wangzhihao
 * @Date 2021-04-06
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ProductCategoryRatation")
public class ProductCategoryRatation extends BaseEntity<ProductCategoryRatation> {

    private static final long serialVersionUID = 1L;

    @TableField("RotationChartName")
    @JSONField(ordinal = 1, name = "RotationChartName")
    private String rotationChartName;

    @TableField("RotationChartSrc")
    @JSONField(ordinal = 1, name = "RotationChartSrc")
    private String rotationChartSrc;

    @TableField("RotationChartUrl")
    @JSONField(ordinal = 1, name = "RotationChartUrl")
    private String rotationChartUrl;

    @TableField("CheckStatus")
    @JSONField(ordinal = 1, name = "CheckStatus")
    private Integer checkStatus;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortId;

    @TableField("CategoryID")
    @JSONField(ordinal = 1, name = "CategoryID")
    private Integer categoryId;

    @TableField("LinkType")
    @JSONField(ordinal = 1, name = "LinkType")
    private Integer linkType;

    @TableField("SeckillProductID")
    @JSONField(ordinal = 1, name = "SeckillProductID")
    private Integer seckillProductId;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productId;

    @TableField("GroupBookingProductID")
    @JSONField(ordinal = 1, name = "GroupBookingProductID")
    private Integer groupBookingProductId;

    public Integer getCheckStatus() {
        if(checkStatus==null){
            return 0;
        }
        return checkStatus;
    }

    public Integer getSortId() {
        if(sortId==null){
            return 99;
        }
        return sortId;
    }

    public Integer getCategoryId() {
        if(categoryId==null){
            return 0;
        }
        return categoryId;
    }

    public Integer getLinkType() {
        if(linkType==null){
            return 0;
        }
        return linkType;
    }

    public Integer getSeckillProductId() {
        if(seckillProductId==null){
            return 0;
        }
        return seckillProductId;
    }

    public Integer getProductId() {
        if(productId==null){
            return 0;
        }
        return productId;
    }

    public Integer getGroupBookingProductId() {
        if(groupBookingProductId==null){
            return 0;
        }
        return groupBookingProductId;
    }
}
