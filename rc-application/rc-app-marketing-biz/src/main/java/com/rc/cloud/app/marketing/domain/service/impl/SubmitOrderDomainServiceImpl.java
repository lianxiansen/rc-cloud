package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrderItem;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrderService;
import com.rc.cloud.app.marketing.domain.entity.regularorder.event.OrderCreatedEvent;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Receiver;
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
    public List<RegularOrder> submitOrder(ComfirmOrder comfirmOrder) {
        List<Product> products = comfirmOrder.getProducts();
        if (CollectionUtils.isEmpty(products)) {
            throw new ServiceException(new ErrorCode(999999, "请选择您需要的商品加入购物车"));
        }
        List<RegularOrder> orders = createRegularOrders(comfirmOrder);
        regularOrderService.insertBatch(orders);
        return orders;
    }

    /**
     * 创建常规订单
     *
     * @param comfirmOrder
     * @return
     */
    private List<RegularOrder> createRegularOrders(ComfirmOrder comfirmOrder) {
        String tradeNo = idRepository.nextId();
        List<RegularOrder> orders = new ArrayList<>();
        for (Product product : comfirmOrder.getProducts()) {
            RegularOrder order = createRegularOrder(comfirmOrder, tradeNo, product);
            orders.add(order);
            applicationContext.publishEvent(new OrderCreatedEvent(order.getId()));
        }
        return orders;
    }

    /**
     * 创建常规订单
     *
     * @param comfirmOrder
     * @param tradeNo
     * @param product
     * @return
     */
    private RegularOrder createRegularOrder(ComfirmOrder comfirmOrder, String tradeNo, Product product) {
        String orderNo = regularOrderService.generateOrderSn(comfirmOrder.getDeliveryAddress().getMobile());
        RegularOrder order = new RegularOrder(idRepository.nextId(), orderNo);
        order.setBuyer(getBuyer(comfirmOrder));
        order.setReceiver(getReceiver(comfirmOrder));
        //计算运费
        order.setFreightAmount(calculateFreightAmount(comfirmOrder));
        order.setTradeNo(tradeNo);
        order.addItems(createRegularOrderItems(comfirmOrder, product, order));
        return order;
    }


    private List<RegularOrderItem> createRegularOrderItems(ComfirmOrder comfirmOrder, Product product, RegularOrder order) {
        List<RegularOrderItem> items = new ArrayList<>();
        comfirmOrder.getItems(product).forEach(item -> {
            RegularOrderItem orderItem = new RegularOrderItem(idRepository.nextId(), order.getId());
            orderItem.setProduct(item.getProduct());
            orderItem.setProductItem(item.getProductItem());
            items.add(orderItem);
        });
        return items;
    }

    private BigDecimal calculateFreightAmount(ComfirmOrder comfirmOrder) {
        List<ProductItem> productItems = comfirmOrder.getItems().stream().map(p -> p.getProductItem()).collect(Collectors.toList());
        DeliveryAddress deliveryAddress = deliveryAddressService.findDefault(Customer.mock().getId());
        if(Objects.isNull(deliveryAddress)){
            throw new ServiceException(99999999,"计算运费失败，失败原因：找不到收货地址");
        }
        BigDecimal freightAmount = calculateFreightAmount(comfirmOrder.getDeliveryType(), productItems, deliveryAddress);
        return freightAmount;
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
        return Buyer.mockBuyer();
    }

    private Receiver getReceiver(ComfirmOrder comfirmOrder) {
        return Receiver.mockReceiver();
    }
}
