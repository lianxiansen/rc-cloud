package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品简要
 * @Author taotianhong
 * @Date 2021/3/25
 * @Description:
 */
@Data
public class SimpleProduct extends BaseEntity<SimpleProduct> {

    @TableField("Name")
    @JSONField(ordinal = 1, name = "Name")
    private String name;

    @TableField("LowestBuy")
    @JSONField(ordinal = 1, name = "LowestBuy")
    private Integer lowestBuy;

    @TableField("MerchantID")
    @JSONField(ordinal = 1, name = "MerchantID")
    private Integer merchantID;

    @TableField("CopartnerID")
    @JSONField(ordinal = 1, name = "CopartnerID")
    private Integer copartnerID;

    @TableField("MasterImage")
    @JSONField(ordinal = 1, name = "MasterImage")
    private String masterImage;

    @TableField("MarkingPrice")
    @JSONField(ordinal = 1, name = "MarkingPrice")
    private BigDecimal markingPrice;

    @TableField("IsDeleted")
    @JSONField(ordinal = 1, name = "IsDeleted")
    private Boolean isDeleted;

    @TableField("IsOnShelf")
    @JSONField(ordinal = 1, name = "IsOnShelf")
    private Integer isOnShelf;

    @TableField("IsOpenFreightCoupon")
    @JSONField(ordinal = 1, name = "IsOpenFreightCoupon")
    private Boolean isOpenFreightCoupon;

    @TableField("IsOpenFullCoupon")
    @JSONField(ordinal = 1, name = "IsOpenFullCoupon")
    private Boolean isOpenFullCoupon;

    @TableField("IsOpenLimitCount")
    @JSONField(ordinal = 1, name = "IsOpenLimitCount")
    private Boolean isOpenLimitCount;

    @TableField("IsRefund")
    @JSONField(ordinal = 1, name = "IsRefund")
    private Boolean isRefund;

    @TableField("LimitCount")
    @JSONField(ordinal = 1, name = "LimitCount")
    private Integer limitCount;

    @TableField("FreightTemplateID")
    @JSONField(ordinal = 1, name = "FreightTemplateID")
    private Integer freightTemplateID;

    @TableField("NoDeliveryTemplateID")
    @JSONField(ordinal = 1, name = "NoDeliveryTemplateID")
    private Integer noDeliveryTemplateID;

    @TableField("ChooseUniformFreight")
    @JSONField(ordinal = 1, name = "ChooseUniformFreight")
    private Boolean chooseUniformFreight;

    @TableField("UniformFreight")
    @JSONField(ordinal = 1, name = "UniformFreight")
    private BigDecimal uniformFreight;

    @TableField("SupplyPrice")
    @JSONField(ordinal = 1, name = "SupplyPrice")
    private BigDecimal supplyPrice;

    @TableField("ProductStatus")
    @JSONField(ordinal = 1, name = "ProductStatus")
    private Integer productStatus;

    @TableField("WareHouseID")
    @JSONField(ordinal = 1, name = "WareHouseID")
    private Integer wareHouseID;

    @TableField("GetIntegral")
    @JSONField(ordinal = 1, name = "GetIntegral")
    private Integer getIntegral;

    @TableField("ProductCategoryIDs")
    @JSONField(ordinal = 1, name = "ProductCategoryIDs")
    private String productCategoryIDs;

    @TableField("PopularizationAmountRate")
    @JSONField(ordinal = 1, name = "PopularizationAmountRate")
    private BigDecimal popularizationAmountRate;

    @TableField("BranchPerformanceRate")
    @JSONField(ordinal = 1, name = "BranchPerformanceRate")
    private BigDecimal branchPerformanceRate;

    @TableField("IsFromAlibaba")
    @JSONField(ordinal = 1, name = "IsFromAlibaba")
    private Boolean isFromAlibaba;

    @TableField("FromAlibabaProductId")
    @JSONField(ordinal = 1, name = "FromAlibabaProductId")
    private String fromAlibabaProductId;

    @TableField("FeedbackRate")
    @JSONField(ordinal = 1, name = "FeedbackRate")
    private Integer feedbackRate;

    @TableField("IsOpenStoreFullFreeShipping")
    @JSONField(ordinal = 1, name = "IsOpenStoreFullFreeShipping")
    private Boolean isOpenStoreFullFreeShipping;

    public Integer getLowestBuy() {
        if(lowestBuy==null){
            return 0;
        }
        return lowestBuy;
    }

    public Integer getMerchantID() {
        if(merchantID==null){
            return 0;
        }
        return merchantID;
    }

    public Integer getCopartnerID() {
        if(copartnerID==null){
            return 0;
        }
        return copartnerID;
    }

    public BigDecimal getMarkingPrice() {
        if(markingPrice==null){
            return BigDecimal.ZERO;
        }
        return markingPrice;
    }

    public Boolean getIsDeleted() {
        if(isDeleted==null){
            return false;
        }
        return isDeleted;
    }

    public Integer getIsOnShelf() {
        if(isOnShelf==null){
            return 0;
        }
        return isOnShelf;
    }

    public Boolean getIsOpenFreightCoupon() {
        if(isOpenFreightCoupon==null){
            return false;
        }
        return isOpenFreightCoupon;
    }

    public Boolean getIsOpenFullCoupon() {
        if(isOpenFullCoupon==null){
            return false;
        }
        return isOpenFullCoupon;
    }

    public Boolean getIsOpenLimitCount() {
        if(isOpenLimitCount==null){
            return false;
        }
        return isOpenLimitCount;
    }

    public Boolean getIsRefund() {
        if(isRefund==null){
            return false;
        }
        return isRefund;
    }

    public Integer getLimitCount() {
        if(limitCount==null){
            return 0;
        }
        return limitCount;
    }

    public Integer getFreightTemplateID() {
        if(freightTemplateID==null){
            return 0;
        }
        return freightTemplateID;
    }

    public Integer getNoDeliveryTemplateID() {
        if(noDeliveryTemplateID==null){
            return 0;
        }
        return noDeliveryTemplateID;
    }

    public Boolean getChooseUniformFreight() {
        if(chooseUniformFreight==null){
            return false;
        }
        return chooseUniformFreight;
    }

    public BigDecimal getUniformFreight() {
        if(uniformFreight==null){
            return BigDecimal.ZERO;
        }
        return uniformFreight;
    }

    public BigDecimal getSupplyPrice() {
        if(supplyPrice==null){
            return BigDecimal.ZERO;
        }
        return supplyPrice;
    }

    public Integer getProductStatus() {
        if(productStatus==null){
            return 0;
        }
        return productStatus;
    }

    public Integer getWareHouseID() {
        if(wareHouseID==null){
            return 0;
        }
        return wareHouseID;
    }

    public Integer getGetIntegral() {
        if(getIntegral==null){
            return 0;
        }
        return getIntegral;
    }

    public BigDecimal getPopularizationAmountRate() {
        if(popularizationAmountRate==null){
            return BigDecimal.ZERO;
        }
        return popularizationAmountRate;
    }

    public BigDecimal getBranchPerformanceRate() {
        if(branchPerformanceRate==null){
            return BigDecimal.ZERO;
        }
        return branchPerformanceRate;
    }

    public Boolean getIsFromAlibaba() {
        if(isFromAlibaba==null){
            return false;
        }
        return isFromAlibaba;
    }

    public Integer getFeedbackRate() {
        if(feedbackRate==null){
            return 0;
        }
        return feedbackRate;
    }

    public Boolean getIsOpenStoreFullFreeShipping() {
        if(isOpenStoreFullFreeShipping==null){
            return false;
        }
        return isOpenStoreFullFreeShipping;
    }
}
