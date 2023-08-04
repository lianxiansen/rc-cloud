package com.rc.cloud.app.marketing.infrastructure.repository.convert;

import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrderItem;
import com.rc.cloud.app.marketing.infrastructure.repository.po.OrderItemPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderItemConvert
 * @Author liandy
 * @Date 2023/8/4 14:46
 * @Description 订单项转换器 订单<->PO
 * @Version 1.0
 */
public class OrderItemConvert {
    public static List<OrderItemPO> convertToPO(List<RegularOrderItem> orderItems) {
        List<OrderItemPO> pos = new ArrayList<>();
        orderItems.forEach(item -> {
            pos.add(convertToPO(item));
        });
        return pos;
    }

    public static OrderItemPO convertToPO(RegularOrderItem orderItem) {
        OrderItemPO po = new OrderItemPO();
        po.setId(orderItem.getId());
        po.setOrderId(orderItem.getOrderId());
        po.setProductId(orderItem.getProduct().getProductId());
        po.setProductName(orderItem.getProduct().getProductName());
        po.setProductImage(orderItem.getProduct().getProductImage());
        po.setProductArticleNo(orderItem.getProduct().getProductArticleNo());
        po.setProductItemId(orderItem.getProductItem().getProductItemId());
        po.setProductItemName(orderItem.getProductItem().getProductItemName());
        po.setProductItemImage(orderItem.getProductItem().getProductItemImage());
        po.setProductItemAttribute(orderItem.getProductItem().getProductItemAttribute());
        po.setProductItemPrice(orderItem.getProductItem().getProductItemPrice());
        po.setProductItemQuantity(orderItem.getProductItem().getProductItemQuantity());
        po.setProductItemAmount(orderItem.getProductItem().getProductItemAmount());
        return po;
    }
}
