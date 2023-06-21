package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ProductCategory")
public class ProductCategory extends BaseEntity<ProductCategory> {

    private static final long serialVersionUID = 1L;

    @TableField("MasterImage")
    @JSONField(ordinal = 1, name = "MasterImage")
    private String masterImage;

    @TableField("Title")
    @JSONField(ordinal = 1, name = "Title")
    private String title;

    @TableField("ParentID")
    @JSONField(ordinal = 1, name = "ParentID")
    private Integer parentId;

    @TableField("Layer")
    @JSONField(ordinal = 1, name = "Layer")
    private Integer layer;

    @TableField("IsLock")
    @JSONField(ordinal = 1, name = "IsLock")
    private Boolean isLock;

    @TableField("LinkUrl")
    @JSONField(ordinal = 1, name = "LinkUrl")
    private String linkUrl;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortId;

    @TableField("IsDeleted")
    @JSONField(ordinal = 1, name = "IsDeleted")
    private Integer isDeleted;

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

    @TableField(exist = false)
    private Integer GrandParentId;

    public Integer getParentId() {
        if(parentId==null){
            return 0;
        }
        return parentId;
    }

    public Integer getLayer() {
        if(layer==null){
            return 0;
        }
        return layer;

    }

    public Boolean getIsLock() {
        if(isLock==null){
            return false;
        }
        return isLock;
    }

    public Integer getSortId() {
        if(sortId==null){
            return 99;
        }
        return sortId;
    }

    public Integer getIsDeleted() {
        if(isDeleted==null){
            return 0;
        }
        return isDeleted;
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

    public Integer getGrandParentId() {
        if(GrandParentId==null){
            return 0;
        }
        return GrandParentId;
    }
}
