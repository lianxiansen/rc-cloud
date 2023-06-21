package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("[Product]")
public class Product extends BaseEntity<Product> {

    private static final long serialVersionUID = 1L;

    @TableField("MerchantID")
    @JSONField(ordinal = 1, name = "MerchantID")
    private Integer merchantID;

    @TableField("ProductStatus")
    @JSONField(ordinal = 1, name = "ProductStatus")
    private Integer productStatus;

    @TableField("Name")
    @JSONField(ordinal = 1, name = "Name")
    private String name;

    @TableField("Number")
    @JSONField(ordinal = 1, name = "Number")
    private String number;

    @TableField("MasterImage")
    @JSONField(ordinal = 1, name = "MasterImage")
    private String masterImage;

    @TableField("Price")
    @JSONField(ordinal = 1, name = "Price")
    private BigDecimal price;

    @TableField("SupplyPrice")
    @JSONField(ordinal = 1, name = "SupplyPrice")
    private BigDecimal supplyPrice;

    @TableField("Weight")
    @JSONField(ordinal = 1, name = "Weight")
    private BigDecimal weight;

    @TableField("IsRecommend")
    @JSONField(ordinal = 1, name = "IsRecommend")
    private Boolean isRecommend;

    @TableField("IsOnShelf")
    @JSONField(ordinal = 1, name = "IsOnShelf")
    private Integer isOnShelf;

    @TableField("Detail")
    @JSONField(ordinal = 1, name = "Detail")
    private String detail;

    @TableField("ProductCategoryIDs")
    @JSONField(ordinal = 1, name = "ProductCategoryIDs")
    private String productCategoryIDs;

    @TableField("ProductCategoryNames")
    @JSONField(ordinal = 1, name = "ProductCategoryNames")
    private String productCategoryNames;

    @TableField("FreightTemplateID")
    @JSONField(ordinal = 1, name = "FreightTemplateID")
    private Integer freightTemplateID;

    @TableField("ShareContent")
    @JSONField(ordinal = 1, name = "ShareContent")
    private String shareContent;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortID;

    @TableField("SEOTitle")
    @JSONField(ordinal = 1, name = "SEOTitle")
    private String seoTitle;

    @TableField("SEOKeyword")
    @JSONField(ordinal = 1, name = "SEOKeyword")
    private String seoKeyword;

    @TableField("SEODescription")
    @JSONField(ordinal = 1, name = "SEODescription")
    private String seoDescription;

    @TableField("IsDeleted")
    @JSONField(ordinal = 1, name = "IsDeleted")
    private Boolean isDeleted;

    @TableField("UniformFreight")
    @JSONField(ordinal = 1, name = "UniformFreight")
    private BigDecimal uniformFreight;

    @TableField("ChooseUniformFreight")
    @JSONField(ordinal = 1, name = "ChooseUniformFreight")
    private Boolean chooseUniformFreight;

    @TableField("DistributorAmount")
    @JSONField(ordinal = 1, name = "DistributorAmount")
    private BigDecimal distributorAmount;

    @TableField("ShareTitle")
    @JSONField(ordinal = 1, name = "ShareTitle")
    private String shareTitle;

    @TableField("ShareDesc")
    @JSONField(ordinal = 1, name = "ShareDesc")
    private String shareDesc;

    @TableField("ProductType")
    @JSONField(ordinal = 1, name = "ProductType")
    private Integer productType;

    @TableField("ShareIcon")
    @JSONField(ordinal = 1, name = "ShareIcon")
    private String shareIcon;

    @TableField("MerchantName")
    @JSONField(ordinal = 1, name = "MerchantName")
    private String merchantName;

    @TableField("WareHouseProductID")
    @JSONField(ordinal = 1, name = "WareHouseProductID")
    private Integer wareHouseProductID;

    @TableField("Sales")
    @JSONField(ordinal = 1, name = "Sales")
    private Integer sales;

    @TableField("BaseSales")
    @JSONField(ordinal = 1, name = "BaseSales")
    private Integer baseSales;

    @TableField("PlaceOfOrigin")
    @JSONField(ordinal = 1, name = "PlaceOfOrigin")
    private String placeOfOrigin;

    @TableField("ProductClassification")
    @JSONField(ordinal = 1, name = "ProductClassification")
    private Integer productClassification;

    @TableField("StoreIsRecommend")
    @JSONField(ordinal = 1, name = "StoreIsRecommend")
    private Integer storeIsRecommend;

    @TableField("StoreSortID")
    @JSONField(ordinal = 1, name = "StoreSortID")
    private Integer storeSortID;

    @TableField("WareHouseID")
    @JSONField(ordinal = 1, name = "WareHouseID")
    private Integer wareHouseID;

    @TableField("MarkingPrice")
    @JSONField(ordinal = 1, name = "MarkingPrice")
    private BigDecimal markingPrice;

    @TableField("IsOpenFreightCoupon")
    @JSONField(ordinal = 1, name = "IsOpenFreightCoupon")
    private Boolean isOpenFreightCoupon;

    @TableField("IsOpenLimitCount")
    @JSONField(ordinal = 1, name = "IsOpenLimitCount")
    private Boolean isOpenLimitCount;

    @TableField("LimitCount")
    @JSONField(ordinal = 1, name = "LimitCount")
    private Integer limitCount;

    @TableField("IsShowPurchaseCountdown")
    @JSONField(ordinal = 1, name = "IsShowPurchaseCountdown")
    private Boolean isShowPurchaseCountdown;

    @TableField("SurplusPercent")
    @JSONField(ordinal = 1, name = "SurplusPercent")
    private String surplusPercent;

    @TableField("IsOpenFullCoupon")
    @JSONField(ordinal = 1, name = "IsOpenFullCoupon")
    private Boolean isOpenFullCoupon;

    @TableField("VideoUrl")
    @JSONField(ordinal = 1, name = "VideoUrl")
    private String videoUrl;

    @TableField("VideoImg")
    @JSONField(ordinal = 1, name = "VideoImg")
    private String videoImg;

    @TableField("IsRefund")
    @JSONField(ordinal = 1, name = "IsRefund")
    private Boolean isRefund;

    @TableField("GetIntegral")
    @JSONField(ordinal = 1, name = "GetIntegral")
    private Integer getIntegral;

    @TableField("IsDistribution")
    @JSONField(ordinal = 1, name = "IsDistribution")
    private Boolean isDistribution;

    @TableField("IsRebate")
    @JSONField(ordinal = 1, name = "IsRebate")
    private Boolean isRebate;

    @TableField("CopartnerID")
    @JSONField(ordinal = 1, name = "CopartnerID")
    private Integer copartnerID;

    @TableField("PopularizationAmountRate")
    @JSONField(ordinal = 1, name = "PopularizationAmountRate")
    private BigDecimal popularizationAmountRate;

    @TableField("BrandID")
    @JSONField(ordinal = 1, name = "BrandID")
    private Integer brandID;

    @TableField("IsAlibabaSelected")
    @JSONField(ordinal = 1, name = "IsAlibabaSelected")
    private Boolean isAlibabaSelected;

    @TableField("FromAlibabaProductID")
    @JSONField(ordinal = 1, name = "FromAlibabaProductID")
    private String fromAlibabaProductID;

    @TableField("FromAlibabaUpplierLoginId")
    @JSONField(ordinal = 1, name = "FromAlibabaUpplierLoginId")
    private String fromAlibabaUpplierLoginId;

    @TableField("IsFollowed")
    @JSONField(ordinal = 1, name = "IsFollowed")
    private Boolean isFollowed;

    @TableField("LowestBuy")
    @JSONField(ordinal = 1, name = "LowestBuy")
    private Integer lowestBuy;

    @TableField("NoDeliveryTemplateID")
    @JSONField(ordinal = 1, name = "NoDeliveryTemplateID")
    private Integer noDeliveryTemplateID;

    @TableField("ManagerID")
    @JSONField(ordinal = 1, name = "ManagerID")
    private Integer managerID;

    @TableField("ManagerName")
    @JSONField(ordinal = 1, name = "ManagerName")
    private String managerName;

    @TableField("CheckStatus")
    @JSONField(ordinal = 1, name = "CheckStatus")
    private Integer checkStatus;

    @TableField("CheckLoginName")
    @JSONField(ordinal = 1, name = "CheckLoginName")
    private String checkLoginName;

    @TableField("CheckLoginIp")
    @JSONField(ordinal = 1, name = "CheckLoginIp")
    private String checkLoginIp;

    @TableField("RefundInfo")
    @JSONField(ordinal = 1, name = "RefundInfo")
    private String refundInfo;

    @TableField("BranchPerformanceRate")
    @JSONField(ordinal = 1, name = "BranchPerformanceRate")
    private BigDecimal branchPerformanceRate;

    @TableField("OffShelfTime")
    @JSONField(ordinal = 1, name = "OffShelfTime")
    private Timestamp offShelfTime;

    @TableField("MaterialText")
    @JSONField(ordinal = 1, name = "MaterialText")
    private String materialText;

    @TableField("UpdateTime")
    @JSONField(ordinal = 1, name = "UpdateTime")
    private Timestamp updateTime;

    @TableField("IsQualityRecommend")
    @JSONField(ordinal = 1, name = "IsQualityRecommend")
    private Boolean isQualityRecommend;

    @TableField("QualityRecommendReason")
    @JSONField(ordinal = 1, name = "QualityRecommendReason")
    private String qualityRecommendReason;

    @TableField("MaterImageFrame")
    @JSONField(ordinal = 1, name = "MaterImageFrame")
    private String materImageFrame;

    @TableField("Keywords")
    @JSONField(ordinal = 1, name = "Keywords")
    private String keywords;

    @TableField("MaterImageFrameBeginTime")
    @JSONField(ordinal = 1, name = "MaterImageFrameBeginTime")
    private Timestamp materImageFrameBeginTime;

    @TableField("MaterImageFrameEndTime")
    @JSONField(ordinal = 1, name = "MaterImageFrameEndTime")
    private Timestamp materImageFrameEndTime;

    @TableField("TopCategory")
    @JSONField(ordinal = 1, name = "TopCategory")
    private String topCategory;

    @TableField("MultipleOrderCount")
    @JSONField(ordinal = 1, name = "MultipleOrderCount")
    private Integer multipleOrderCount;

    @TableField("FeedbackRate")
    @JSONField(ordinal = 1, name = "FeedbackRate")
    private Integer feedbackRate;

    @TableField("IsOpenStoreFullFreeShipping")
    @JSONField(ordinal = 1, name = "IsOpenStoreFullFreeShipping")
    private Boolean isOpenStoreFullFreeShipping;

    @TableField("SettedSimiliarProducts")
    @JSONField(ordinal = 1, name = "SettedSimiliarProducts")
    private String settedSimiliarProducts;

    @TableField("IsDeliveryRefund")
    @JSONField(ordinal = 1, name = "IsDeliveryRefund")
    private Boolean isDeliveryRefund;

    @TableField("ProductSKU")
    @JSONField(ordinal = 1, name = "ProductSKU")
    private String productSKU;

    @TableField("TicketRemark")
    @JSONField(ordinal = 1, name = "TicketRemark")
    private String ticketRemark;

    @TableField("TicketType")
    @JSONField(ordinal = 1, name = "TicketType")
    private Integer ticketType;

    public Boolean getIsRefund() {
        if(isRefund==null){
            return false;
        }
        return isRefund;
    }

    public Boolean getIsDeliveryRefund() {
        if(isDeliveryRefund==null){
            return false;
        }
        return isDeliveryRefund;
    }


    public Integer getMerchantID() {
        if(merchantID==null){
            return 0;
        }
        return merchantID;
    }

    public Integer getProductStatus() {
        if(productStatus==null){
            return 0;
        }
        return productStatus;
    }

    public BigDecimal getPrice() {
        if(price==null){
            return BigDecimal.ZERO;
        }
        return price;
    }

    public BigDecimal getSupplyPrice() {
        if(supplyPrice==null){
            return BigDecimal.ZERO;
        }
        return supplyPrice;
    }

    public BigDecimal getWeight() {
        if(weight==null){
            return BigDecimal.ZERO;
        }
        return weight;
    }

    public Boolean getIsRecommend() {
        if(isRecommend==null){
            return false;
        }
        return isRecommend;
    }

    public Integer getIsOnShelf() {
        if(isOnShelf==null){
            return 0;
        }
        return isOnShelf;
    }

    public Integer getFreightTemplateID() {
        if(freightTemplateID==null){
            return 0;
        }
        return freightTemplateID;
    }

    public Integer getSortID() {
        if(sortID==null){
            return 99;
        }
        return sortID;
    }

    public Boolean getIsDeleted() {
        if(isDeleted==null){
            return false;
        }
        return isDeleted;
    }

    public BigDecimal getUniformFreight() {
        if(uniformFreight==null){
            return BigDecimal.ZERO;
        }
        return uniformFreight;
    }

    public Boolean getChooseUniformFreight() {
        if(chooseUniformFreight==null){
            return false;
        }
        return chooseUniformFreight;
    }

    public BigDecimal getDistributorAmount() {
        if(distributorAmount==null){
            return BigDecimal.ZERO;
        }
        return distributorAmount;
    }

    public Integer getProductType() {
        if(productType==null){
            return 0;
        }
        return productType;
    }

    public Integer getWareHouseProductID() {
        if(wareHouseProductID==null){
            return 0;
        }
        return wareHouseProductID;
    }

    public Integer getSales() {
        if(sales==null){
            return 0;
        }
        return sales;
    }

    public Integer getBaseSales() {
        if(baseSales==null){
            return 0;
        }
        return baseSales;
    }

    public Integer getProductClassification() {
        if(productClassification==null){
            return 0;
        }
        return productClassification;
    }

    public Integer getStoreIsRecommend() {
        if(storeIsRecommend==null){
            return 0;
        }
        return storeIsRecommend;
    }

    public Integer getStoreSortID() {
        if(storeSortID==null){
            return 99;
        }
        return storeSortID;
    }

    public Integer getWareHouseID() {
        if(wareHouseID==null){
            return 0;
        }
        return wareHouseID;
    }

    public BigDecimal getMarkingPrice() {
        if(markingPrice==null){
            return BigDecimal.ZERO;
        }
        return markingPrice;
    }

    public Boolean getIsOpenFreightCoupon() {
        if(isOpenFreightCoupon==null){
            return false;
        }
        return isOpenFreightCoupon;
    }

    public Boolean getIsOpenLimitCount() {
        if(isOpenLimitCount==null){
            return false;
        }
        return isOpenLimitCount;
    }

    public Integer getLimitCount() {
        if(limitCount==null){
            return 0;
        }
        return limitCount;
    }

    public Boolean getIsShowPurchaseCountdown() {
        if(isShowPurchaseCountdown==null){
            return false;
        }
        return isShowPurchaseCountdown;
    }

    public Boolean getIsOpenFullCoupon() {
        if(isOpenFullCoupon==null){
            return false;
        }
        return isOpenFullCoupon;
    }

    public Integer getGetIntegral() {
        if(getIntegral==null){
            return 0;
        }
        return getIntegral;
    }

    public Boolean getIsDistribution() {
        if(isDistribution==null){
            return false;
        }
        return isDistribution;
    }

    public Boolean getIsRebate() {
        if(isRebate==null){
            return false;
        }
        return isRebate;
    }

    public Integer getCopartnerID() {
        if(copartnerID==null){
            return 0;
        }
        return copartnerID;
    }

    public BigDecimal getPopularizationAmountRate() {
        if(popularizationAmountRate==null){
            return BigDecimal.ZERO;
        }
        return popularizationAmountRate;
    }

    public Integer getBrandID() {
        if(brandID==null){
            return 0;
        }
        return brandID;
    }

    public Boolean getIsAlibabaSelected() {
        if(isAlibabaSelected==null){
            return false;
        }
        return isAlibabaSelected;
    }

    public Boolean getIsFollowed() {
        if(isFollowed==null){
            return false;
        }
        return isFollowed;
    }

    public Integer getLowestBuy() {
        if(lowestBuy==null){
            return 0;
        }
        return lowestBuy;
    }

    public Integer getNoDeliveryTemplateID() {
        if(noDeliveryTemplateID==null){
            return 0;
        }
        return noDeliveryTemplateID;
    }

    public Integer getManagerID() {
        if(managerID==null){
            return 0;
        }
        return managerID;
    }

    public Integer getCheckStatus() {
        if(checkStatus==null){
            return 0;
        }
        return checkStatus;
    }

    public BigDecimal getBranchPerformanceRate() {
        if(branchPerformanceRate==null){
            return BigDecimal.ZERO;
        }
        return branchPerformanceRate;
    }

    public Timestamp getOffShelfTime() {
        if(offShelfTime==null){
            return new Timestamp(0);
        }
        return offShelfTime;
    }

    public Timestamp getUpdateTime() {
        if(updateTime==null){
            return new Timestamp(0);
        }
        return updateTime;
    }

    public Boolean getIsQualityRecommend() {
        if(isQualityRecommend==null){
            return false;
        }
        return isQualityRecommend;
    }

    public Timestamp getMaterImageFrameBeginTime() {
        if(materImageFrameBeginTime==null){
            return new Timestamp(0);
        }
        return materImageFrameBeginTime;
    }

    public Timestamp getMaterImageFrameEndTime() {
        if(materImageFrameEndTime==null){
            return new Timestamp(0);
        }
        return materImageFrameEndTime;
    }

    public Integer getMultipleOrderCount() {
        if(multipleOrderCount==null){
            return 0;
        }
        return multipleOrderCount;
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

    public Integer getTicketType() {
        if(ticketType==null){
            return 0;
        }
        return ticketType;
    }


}
