package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


/**
 * <p>
 *
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("Item")
public class Item extends BaseEntity<Item> {

    private static final long serialVersionUID = 1L;

    @TableField("Price")
    @JSONField(ordinal = 1 ,name = "Price")
    private BigDecimal price;

    @TableField("Weight")
    @JSONField(ordinal = 1 ,name = "Weight")
    private BigDecimal weight;

    @TableField("Inventory")
    @JSONField(ordinal = 1 ,name = "Inventory")
    private Integer inventory;

    @TableField("IsLock")
    @JSONField(ordinal = 1 ,name = "IsLock")
    private Boolean isLock;

    @TableField("SpecificationCombinationName")
    @JSONField(ordinal = 1 ,name = "SpecificationCombinationName")
    private String specificationCombinationName;

    @TableField("SpecificationCombinationID")
    @JSONField(ordinal = 1 ,name = "SpecificationCombinationID")
    private String specificationCombinationID;

    @TableField("ProductID")
    @JSONField(ordinal = 1 ,name = "ProductID")
    private Integer productID;

    @TableField("Number")
    @JSONField(ordinal = 1 ,name = "Number")
    private String number;

    @TableField("SupplyPrice")
    @JSONField(ordinal = 1 ,name = "SupplyPrice")
    private BigDecimal supplyPrice;

    @TableField("ImageUrl")
    @JSONField(ordinal = 1 ,name = "ImageUrl")
    private String imageUrl;

    @TableField("PartnerPrice")
    @JSONField(ordinal = 1 ,name = "PartnerPrice")
    private BigDecimal partnerPrice;

    @TableField("FromAlibabaSkuId")
    @JSONField(ordinal = 1 ,name = "FromAlibabaSkuId")
    private String fromAlibabaSkuId;

    @TableField("FromAlibabaSpecId")
    @JSONField(ordinal = 1,name = "FromAlibabaSpecId")
    private String fromAlibabaSpecId;

    @TableField("WareHouseItemID")
    @JSONField(ordinal = 1 ,name = "WareHouseItemID")
    private Integer wareHouseItemID;

    @TableField("CodeNumber")
    @JSONField(ordinal = 1 ,name = "CodeNumber")
    private String codeNumber;

    @TableField("IsComplex")
    @JSONField(ordinal = 1 ,name = "IsComplex")
    private Boolean isComplex;

    @TableField("FromAlibabaSpecNumber")
    @JSONField(ordinal = 1 ,name = "FromAlibabaSpecNumber")
    private String fromAlibabaSpecNumber;

    @TableField("IsPostageFree")
    @JSONField(ordinal = 1 ,name = "IsPostageFree")
    private Boolean isPostageFree;

    public BigDecimal getPrice() {
        if(price==null){
            return BigDecimal.ZERO;
        }
        return price;
    }

    public BigDecimal getWeight() {
        if(weight==null){
            return BigDecimal.ZERO;
        }
        return weight;
    }

    public Integer getInventory() {
        if(inventory==null){
            return 0;
        }
        return inventory;
    }

    public Boolean getIsLock() {
        if(isLock==null){
            return false;
        }
        return isLock;
    }

    public Integer getProductID() {
        if(productID==null){
            return 0;
        }
        return productID;
    }

    public BigDecimal getSupplyPrice() {
        if(supplyPrice==null){
            return BigDecimal.ZERO;
        }
        return supplyPrice;
    }

    public BigDecimal getPartnerPrice() {
        if(partnerPrice==null){
            return BigDecimal.ZERO;
        }
        return partnerPrice;
    }

    public Integer getWareHouseItemID() {
        if(wareHouseItemID==null){
            return 0;
        }
        return wareHouseItemID;
    }

    public Boolean getIsComplex() {
        if(isComplex==null){
            return false;
        }
        return isComplex;
    }

    public Boolean getIsPostageFree() {
        if(isPostageFree==null){
            return false;
        }
        return isPostageFree;
    }
}
