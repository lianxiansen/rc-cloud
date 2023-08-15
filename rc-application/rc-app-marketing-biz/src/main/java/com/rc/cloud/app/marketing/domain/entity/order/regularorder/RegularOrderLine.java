package com.rc.cloud.app.marketing.domain.entity.order.regularorder;

import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.OrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;

/**
 * @ClassName OrderItem
 * @Author liandy
 * @Date 2023/7/28 17:25
 * @Description 常规订单项
 * @Version 1.0
 */
public class RegularOrderLine extends OrderLine {

    /**
     * 订单标识
     */
    private String regularOrderId;


    public RegularOrderLine(String id, String regularOrderId, Product product, ProductQuality quality) {
        super(id,product,quality);
        this.regularOrderId = regularOrderId;

    }

    @Override
    public Product getProduct() {
        return product;
    }




    public String getRegularOrderId() {
        return regularOrderId;
    }

}

