package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author wangzhihao
 * @Date 2021-04-06
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CategoryFindGoods")
public class CategoryFindGoods extends BaseEntity<CategoryFindGoods> {

    private static final long serialVersionUID = 1L;

    @TableField("ProductID")
    @JSONField(ordinal = 1, name = "ProductID")
    private Integer productId;

    @TableField("CategoryID")
    @JSONField(ordinal = 1, name = "CategoryID")
    private Integer categoryId;

    @TableField("ProductName")
    @JSONField(ordinal = 1, name = "ProductName")
    private String productName;

    @TableField("ProductPrice")
    @JSONField(ordinal = 1, name = "ProductPrice")
    private BigDecimal productPrice;

    @TableField("ProductMasterImage")
    @JSONField(ordinal = 1, name = "ProductMasterImage")
    private String productMasterImage;

    public Integer getProductId() {
        if(productId==null){
            return 0;
        }
        return productId;
    }

    public Integer getCategoryId() {
        if(categoryId==null){
            return 0;
        }
        return categoryId;
    }

    public BigDecimal getProductPrice() {
        if(productPrice==null){
            return BigDecimal.ZERO;
        }
        return productPrice;
    }
}
