package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@Data
@Accessors(chain = true)
public class SeckillSkuBO {

    private int seckillLimitBuy;

    private BigDecimal seckillPrice;

    private int seckillInventory;

    private int seckillTotalInventory;
}
