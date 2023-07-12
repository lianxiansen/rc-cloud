package com.rc.cloud.app.operate.infrastructure.persistence.po;

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

    @TableField("id")
    private Long Id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;

    @TableField("name")
    private String name;

    @TableField("ip")
    private String ip;

    @TableField("operate_time")
    private LocalDateTime operateTime;

}
