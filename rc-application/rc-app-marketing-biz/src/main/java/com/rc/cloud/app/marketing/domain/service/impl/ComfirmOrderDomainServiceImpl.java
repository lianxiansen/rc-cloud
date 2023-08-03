package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderItem;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ComfirmOrderDomainService
 * @Author liandy
 * @Date 2023/7/28 13:45
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ComfirmOrderDomainServiceImpl implements ComfirmOrderDomainService {
    @Resource
    private IdRepository idRepository;

    @Resource
    private ComfirmOrderRepository comfirmOrderRepository;

    @Resource
    private DeliveryAddressService deliveryAddressService;

    @Override
    public ComfirmOrder placeOrder(List<Cart> carts) {
        AssertUtils.assertArgumentTrue(!CollectionUtils.isEmpty(carts),"carts is not empty");
        String customerId=carts.get(0).getUserId().id();
        ComfirmOrder comfirmOrder = new ComfirmOrder(idRepository.nextId());
        DeliveryAddress deliveryAddress = deliveryAddressService.findDefaultDeliveryAddress(customerId);
        comfirmOrder.setDeliveryAddress(deliveryAddress);
        //确认订单-商品信息
        carts.forEach(cartItem -> {
            comfirmOrder.addItem(new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(), Product.mockProductA(), ProductItem.mockProductItemA1()));
            comfirmOrder.addItem(new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(), Product.mockProductA(), ProductItem.mockProductItemA2()));
            comfirmOrder.addItem(new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(), Product.mockProductB(), ProductItem.mockProductItemB1()));
            comfirmOrder.addItem(new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(), Product.mockProductB(), ProductItem.mockProductItemB2()));
        });
        comfirmOrder.setPayType(0);
        comfirmOrder.setDeliveryType(DeliveryType.CONSIGN);
        comfirmOrder.setFreightAmount(BigDecimal.ZERO);
        comfirmOrderRepository.save(comfirmOrder);
        return comfirmOrder;
    }





}
