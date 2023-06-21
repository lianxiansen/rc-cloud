package com.rc.cloud.app.mall.infrastructure.persistence.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 商品详情
 * @Author taotianhong
 * @Date 2021/3/25
 * @Description:
 */
@Data
public class ProductDetail extends BaseEntity<ProductDetail> {

    @TableField("Detail")
    @JSONField(ordinal = 1, name = "Detail")
    private String detail;
}
