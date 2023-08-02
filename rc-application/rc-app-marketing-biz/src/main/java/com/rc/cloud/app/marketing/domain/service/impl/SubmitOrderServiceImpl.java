package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.Buyer;
import com.rc.cloud.app.marketing.domain.entity.order.Order;
import com.rc.cloud.app.marketing.domain.entity.order.OrderItem;
import com.rc.cloud.app.marketing.domain.entity.order.Receiver;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SubmitOrderServiceImpl
 * @Author liandy
 * @Date 2023/8/2 08:05
 * @Description 提交订单服务
 * @Version 1.0
 */
@Service
public class SubmitOrderServiceImpl implements SubmitOrderService {
    @Resource
    private IdRepository idRepository;
    @Override
    public Order createOrder(ComfirmOrder comfirmOrder) {
        String orderNo = "10223116785";
        Order order = new Order(idRepository.nextId(), orderNo);
        order.setBuyer(getBuyer(comfirmOrder));
        order.setReceiver(getReceiver(comfirmOrder));
        comfirmOrder.getItems().forEach(item -> {
            OrderItem orderItem = new OrderItem(idRepository.nextId(), order.getId());
            orderItem.setProductItem(item.getProductItem());
            order.addItem(orderItem);
        });
        //计算运费
        List<ProductItem> productItems = comfirmOrder.getItems().stream().map(p -> p.getProductItem()).collect(Collectors.toList());
        BigDecimal freightAmount = calculateFreightAmount(comfirmOrder.getDeliveryType(), productItems, null);
        order.setFreightAmount(freightAmount);
        return order;
    }
    /**
     * 计算运费
     *
     * @param deliveryType
     * @param productItems
     * @param deliveryAddress
     * @return
     */
    private BigDecimal calculateFreightAmount(DeliveryType deliveryType, List<ProductItem> productItems, DeliveryAddress deliveryAddress) {

        return BigDecimal.ZERO;
    }



    private Buyer getBuyer(ComfirmOrder comfirmOrder) {
        return new Buyer("陈激扬", "去11", "18258687039");
    }

    private Receiver getReceiver(ComfirmOrder comfirmOrder) {
        return new Receiver("某某某", "浙江省台州市黄岩区王西路41号", "13812345678");
    }
}
