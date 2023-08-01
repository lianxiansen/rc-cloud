package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("product_operate")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductOperatePO {

    private static final long serialVersionUID = 256345L;

    private String id;

    @TableField("product_id")
    private String productId;

    @TableField("name")
    private String name;

    @TableField("ip")
    private String ip;

    @TableField("operate_time")
    private LocalDateTime operateTime;

}
