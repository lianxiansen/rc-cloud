package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxun.qlive.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ProductFullCoupon")
public class ProductFullCoupon extends BaseEntity<ProductFullCoupon> {

    private static final long serialVersionUID = 1L;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productID;

    @TableField("FullAmount")
    @JSONField(ordinal = 1, name = "FullAmount")
    private BigDecimal fullAmount;

    @TableField("ReduceAmount")
    @JSONField(ordinal = 1, name = "ReduceAmount")
    private BigDecimal reduceAmount;

    @TableField("IsTimeLimit")
    @JSONField(ordinal = 1, name = "IsTimeLimit")
    private Boolean isTimeLimit;

    @TableField("BeginTime")
    @JSONField(ordinal = 1, name = "BeginTime")
    private Timestamp beginTime;

    @TableField("EndTime")
    @JSONField(ordinal = 1, name = "EndTime")
    private Timestamp endTime;

    @TableField("IsDoubling")
    @JSONField(ordinal = 1, name = "IsDoubling")
    private Boolean isDoubling;

    public Integer getProductID() {
        if(productID==null){
            return 0;
        }
        return productID;
    }

    public BigDecimal getFullAmount() {
        if(fullAmount==null){
            return BigDecimal.ZERO;
        }
        return fullAmount;
    }

    public BigDecimal getReduceAmount() {
        if(reduceAmount==null){
            return BigDecimal.ZERO;
        }
        return reduceAmount;
    }

    public Boolean getIsTimeLimit() {
        if(isTimeLimit==null){
            return false;
        }
        return isTimeLimit;
    }

    //@JSONField(deserializeUsing = TimestampJsonDeserializer.class)
    public Timestamp getBeginTime() {
        if(beginTime==null){
            return new Timestamp(0);
        }
        return beginTime;
    }

    public Timestamp getEndTime() {
        if(endTime==null){
            return new Timestamp(0);
        }
        return endTime;
    }

    public Boolean getIsDoubling() {
        if(isDoubling==null){
            return false;
        }
        return isDoubling;
    }
}
