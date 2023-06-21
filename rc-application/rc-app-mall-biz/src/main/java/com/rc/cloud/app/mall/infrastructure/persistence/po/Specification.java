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
@TableName("Specification")
public class Specification extends BaseEntity<Specification> {

    private static final long serialVersionUID = 1L;

    @TableField("Title")
    @JSONField(ordinal = 1, name = "Title")
    private String title;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortID;

    @TableField("MerchantID")
    @JSONField(ordinal = 1, name = "MerchantID")
    private Integer merchantID;

    @TableField("CopartnerID")
    @JSONField(ordinal = 1, name = "CopartnerID")
    private Integer copartnerID;

    public Specification() {
        this.title = "";
        this.sortID = 99;
        this.merchantID = 0;
        this.copartnerID = 0;
    }

    public Specification(String title, Integer sortId, Integer merchantId) {
        this.title = title;
        this.sortID = sortId;
        this.merchantID = merchantId;
    }

    public Integer getSortID() {
        if(sortID==null){
            return 99;
        }
        return sortID;
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
}
