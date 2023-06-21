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
@TableName("SpecificationValue")
public class SpecificationValue extends BaseEntity<SpecificationValue> {

    private static final long serialVersionUID = 1L;

    @TableField("Title")
    @JSONField(ordinal = 1, name = "Title")
    private String title;

    @TableField("SpecificationID")
    @JSONField(ordinal = 1, name = "SpecificationID")
    private Integer specificationID;

    @TableField("SortID")
    @JSONField(ordinal = 1, name = "SortID")
    private Integer sortID;

    public SpecificationValue() {
        this.title = "";
        this.specificationID = 0;
        this.sortID = 99;
    }

    public SpecificationValue(String title, Integer specificationId, Integer sortId) {
        this.title = title;
        this.specificationID = specificationId;
        this.sortID = sortId;
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
}
