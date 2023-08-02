package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderDTO;
import com.rc.cloud.app.marketing.application.service.OrderApplicationService;
import com.rc.cloud.app.marketing.domain.entity.cartmock.CartService;
import com.rc.cloud.app.marketing.domain.entity.cartmock.Cart;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderService;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderApplicationServiceImpl
 * @Author liandy
 * @Date 2023/7/29 14:48
 * @Description TODO
 * @Version 1.0
 */
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Autowired
    private ComfirmOrderService comfirmOrderDomainService;
    @Autowired
    private DeliveryAddressService deliveryAddressDomainService;
    @Autowired
    private CartService cartDomainService;
    @Resource
    private IdRepository idRepository;

    @Override
    public ComfirmOrderBO placeOrder(PlaceOrderDTO placeOrderDTO) {
        List<Cart> cartItems = cartDomainService.findCarts(placeOrderDTO.getCartItemIds());
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(cartItems);
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findDefault("");
        return new ComfirmOrderBO(comfirmOrder,deliveryAddress);
    }
}
