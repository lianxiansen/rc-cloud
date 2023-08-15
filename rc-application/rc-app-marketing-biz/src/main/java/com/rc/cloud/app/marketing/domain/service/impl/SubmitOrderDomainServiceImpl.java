package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderService;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.event.OrderCreatedEvent;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Receiver;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.exception.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private RegularOrderService regularOrderService;

    @Resource
    private DeliveryAddressService deliveryAddressService;

    @Override
    public List<RegularOrder> submitOrder(Customer customer, ComfirmOrder comfirmOrder) {
        List<Product> products = comfirmOrder.getProducts();
        if (CollectionUtils.isEmpty(products)) {
            throw new ServiceException(new ErrorCode(999999, "请选择您需要的商品加入购物车"));
        }
        List<RegularOrder> orders = createRegularOrdersGroupProduct(customer, comfirmOrder);
        regularOrderService.insertBatch(orders);
        return orders;
    }

    /**
     * 按商品分组创建常规订单
     *
     * @param comfirmOrder
     * @return
     */
    private List<RegularOrder> createRegularOrdersGroupProduct(Customer customer, ComfirmOrder comfirmOrder) {
        String tradeNo = idRepository.nextId();
        List<RegularOrder> orders = new ArrayList<>();
        List<String> productIds=comfirmOrder.getProducts().stream().map(p->p.getProductId()).distinct().collect(Collectors.toList());
        productIds.forEach(productId->{
            RegularOrder order = createRegularOrder(customer, comfirmOrder, tradeNo, productId);
            orders.add(order);
            applicationContext.publishEvent(new OrderCreatedEvent(order.getId()));
        });
        return orders;
    }

    /**
     * 创建常规订单
     *
     * @param comfirmOrder
     * @param tradeNo
     * @param productId
     * @return
     */
    private RegularOrder createRegularOrder(Customer customer, ComfirmOrder comfirmOrder, String tradeNo, String productId) {
        String orderNo = regularOrderService.generateOrderSn();
        RegularOrder regularOrder = new RegularOrder(idRepository.nextId(), orderNo);
        regularOrder.setBuyer(getBuyer(comfirmOrder));
        regularOrder.setReceiver(getReceiver(comfirmOrder));
        regularOrder.setTradeNo(tradeNo);
        List<ComfirmOrderLine> comfirmOrderLines= comfirmOrder.getLines().stream().filter(item->item.getProduct().getProductId().equals(productId)).collect(Collectors.toList());
        comfirmOrderLines.forEach(comfirmOrderLine -> {
            regularOrder.addLine(createRegularOrderLines(regularOrder,comfirmOrderLine));
        });

        //计算运费
        regularOrder.setFreightAmount(calculateFreightAmount(comfirmOrder));
        return regularOrder;
    }


    private RegularOrderLine createRegularOrderLines(RegularOrder regularOrder, ComfirmOrderLine  comfirmOrderLine) {
        RegularOrderLine orderItem = new RegularOrderLine(idRepository.nextId(), regularOrder.getId(),comfirmOrderLine.getProduct(),comfirmOrderLine.getProductQuality());
        return orderItem;
    }

    private BigDecimal calculateFreightAmount(ComfirmOrder comfirmOrder) {
        DeliveryAddress deliveryAddress = deliveryAddressService.findDefault(Customer.mock());
        if (Objects.isNull(deliveryAddress)) {
            throw new ServiceException(99999999, "计算运费失败，失败原因：找不到收货地址");
        }
        BigDecimal freightAmount = calculateFreightAmount(comfirmOrder.getDeliveryType(), comfirmOrder.getProducts(), deliveryAddress);
        return freightAmount;
    }

    /**
     * 计算运费
     *
     * @param deliveryType
     * @param deliveryAddress
     * @return
     */
    private BigDecimal calculateFreightAmount(DeliveryType deliveryType, List<Product> products, DeliveryAddress deliveryAddress) {

        return BigDecimal.ZERO;
    }


    private Buyer getBuyer(ComfirmOrder comfirmOrder) {
        return Buyer.mockBuyer();
    }

    private Receiver getReceiver(ComfirmOrder comfirmOrder) {
        return Receiver.mockReceiver();
    }
}
