package com.rc.cloud.app.marketing.infrastructure.repository.convert;

import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderLine;
import com.rc.cloud.app.marketing.infrastructure.repository.po.RegularOrderLinePO;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderItemConvert
 * @Author liandy
 * @Date 2023/8/4 14:46
 * @Description 订单项转换器 订单<->PO
 * @Version 1.0
 */
public class RegularOrderLineConvert {
    public static List<RegularOrderLinePO> convertToPO(List<RegularOrderLine> orderItems) {
        List<RegularOrderLinePO> pos = new ArrayList<>();
        orderItems.forEach(item -> {
            pos.add(convertToPO(item));
        });
        return pos;
    }

    public static RegularOrderLinePO convertToPO(RegularOrderLine orderItem) {
        RegularOrderLinePO po = new RegularOrderLinePO();
        po.setId(orderItem.getId());
        po.setRegularOrderId(orderItem.getRegularOrderId());
        po.setProductId(orderItem.getProduct().getProductId());
        po.setProductName(orderItem.getProduct().getProductName());
        po.setProductImage(orderItem.getProduct().getProductImage());
        po.setProductArticleNo(orderItem.getProduct().getProductArticleNo());
        po.setProductAttribute(orderItem.getProduct().getProductAttribute());
        po.setProductPrice(orderItem.getProduct().getProductPrice());
        po.setProductQuality(orderItem.getProductQuality().getValue());
        po.setProductAmount(orderItem.getProductAmount());
        return po;
    }
}
