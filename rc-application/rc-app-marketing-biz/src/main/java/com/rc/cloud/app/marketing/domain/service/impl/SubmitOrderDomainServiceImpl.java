package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.Buyer;
import com.rc.cloud.app.marketing.domain.entity.order.Order;
import com.rc.cloud.app.marketing.domain.entity.order.OrderItem;
import com.rc.cloud.app.marketing.domain.entity.order.Receiver;
import com.rc.cloud.app.marketing.domain.entity.order.event.OrderCreatedEvent;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
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
public class SubmitOrderDomainServiceImpl implements SubmitOrderDomainService {
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public List<Order> submitOrder(ComfirmOrder comfirmOrder) {
        List<Product> products=comfirmOrder.getProducts();
        if(CollectionUtils.isEmpty(products)){
            throw new ServiceException(new ErrorCode(999999,"请选择您需要的商品加入购物车"));
        }
        List<Order> orders= Collections.emptyList();
        for (Product product : products) {
            String orderNo = "10223116785";
            Order order = new Order(idRepository.nextId(), orderNo,idRepository.nextId());
            order.setBuyer(getBuyer(comfirmOrder));
            order.setReceiver(getReceiver(comfirmOrder));
            comfirmOrder.getItems(product).forEach(item -> {
                OrderItem orderItem = new OrderItem(idRepository.nextId(), order.getId());
                orderItem.setProductItem(item.getProductItem());
                order.addItem(orderItem);
            });
            //计算运费
            List<ProductItem> productItems = comfirmOrder.getItems().stream().map(p -> p.getProductItem()).collect(Collectors.toList());
            BigDecimal freightAmount = calculateFreightAmount(comfirmOrder.getDeliveryType(), productItems, null);
            order.setFreightAmount(freightAmount);
            applicationContext.publishEvent(new OrderCreatedEvent(order.getId()));
            orders.add(order);
        }
        return orders;
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
        return new Buyer("123","陈激扬", "去11", "18258687039");
    }

    private Receiver getReceiver(ComfirmOrder comfirmOrder) {
        return new Receiver("某某某", "浙江省台州市黄岩区王西路41号", "13812345678");
    }
}
