package com.rc.cloud.app.product.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxun.qlive.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 商品相册
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ProductAlbum")
public class ProductAlbum extends BaseEntity<ProductAlbum> {

    private static final long serialVersionUID = 1L;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productID;

    @TableField("Image")
    @JSONField(ordinal = 1, name = "Image")
    private String image;

    @TableField("Name")
    @JSONField(ordinal = 1, name = "Name")
    private String name;

    @TableField("Remark")
    @JSONField(ordinal = 1, name = "Remark")
    private String remark;

    @TableField("IsDefault")
    @JSONField(ordinal = 1, name = "IsDefault")
    private Boolean isDefault;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortID;

    public Integer getProductID() {
        if(productID==null){
            return 0;
        }
        return productID;
    }

    public Boolean getIsDefault() {
        if(isDefault==null){
            return false;
        }
        return isDefault;
    }

    public Integer getSortID() {
        if(sortID==null){
            return 99;
        }
        return sortID;
    }
}
