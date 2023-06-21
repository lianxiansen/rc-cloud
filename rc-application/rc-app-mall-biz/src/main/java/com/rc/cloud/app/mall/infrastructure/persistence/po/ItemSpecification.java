package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ItemSpecification")
public class ItemSpecification extends BaseEntity<ItemSpecification> {

    private static final long serialVersionUID = 1L;

    @TableField("ItemID")
    @JSONField(ordinal = 1, name = "ItemID")
    private Integer itemID;

    @TableField("SpecificationValueID")
    @JSONField(ordinal = 1, name = "SpecificationValueID")
    private Integer specificationValueID;

    @TableField("SpecificationID")
    @JSONField(ordinal = 1, name = "SpecificationID")
    private Integer specificationID;

    @TableField("SpecificationTitle")
    @JSONField(ordinal = 1, name = "SpecificationTitle")
    private String specificationTitle;

    @TableField("SpecificationContent")
    @JSONField(ordinal = 1, name = "SpecificationContent")
    private String specificationContent;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortID;

    @TableField("SortIDForTitle")
    @JSONField(ordinal = 1, name = "SortIDForTitle")
    private Integer sortIDForTitle;

    @TableField("IsAlibabaSelected")
    @JSONField(ordinal = 1, name = "IsAlibabaSelected")
    private Boolean isAlibabaSelected;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productID;

    public Integer getItemID() {
        if(itemID==null){
            return 0;
        }
        return itemID;
    }

    public Integer getSpecificationValueID() {
        if(specificationValueID==null){
            return 0;
        }
        return specificationValueID;
    }

    public Integer getSpecificationID() {
        if(specificationID==null){
            return 0;
        }
        return specificationID;
    }

    public Integer getSortID() {
        if(sortID==null){
            return 99;
        }
        return sortID;
    }

    public Integer getSortIDForTitle() {
        if(sortIDForTitle==null){
            return 0;
        }
        return sortIDForTitle;
    }

    public Boolean getIsAlibabaSelected() {
        if(isAlibabaSelected==null){
            return false;
        }
        return isAlibabaSelected;
    }

    public Integer getProductID() {
        if(productID==null){
            return 0;
        }
        return productID;
    }
}
