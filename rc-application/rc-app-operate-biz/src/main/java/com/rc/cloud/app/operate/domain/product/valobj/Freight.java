package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

import java.math.BigDecimal;

/**
 * @ClassName: FreeShipping
 * @Author: liandy
 * @Date: 2023/6/26 13:39
 * @Description: TODO
 */
public class Freight extends ValueObject {
    private String type;
    private String templateId;
    private BigDecimal price;
}
