package com.rc.cloud.app.product.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxun.qlive.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ProductFreightCoupon")
public class ProductFreightCoupon extends BaseEntity<ProductFreightCoupon> {

    private static final long serialVersionUID = 1L;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productID;

    @TableField("FullAmount")
    @JSONField(ordinal = 1, name = "FullAmount")
    private Integer fullAmount;

    @TableField("AreaNameList")
    @JSONField(ordinal = 1, name = "AreaNameList")
    private String areaNameList;

    public Integer getProductID() {
        if(productID==null){
            return 0;
        }
        return productID;
    }

    public Integer getFullAmount() {
        if(fullAmount==null){
            return 0;
        }
        return fullAmount;
    }
}
