package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithPrductDTO;
import com.rc.cloud.app.marketing.application.service.OrderApplicationService;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartService;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.service.impl.ComfirmOrderDomainServiceImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName OrderApplicationServiceImpl
 * @Author liandy
 * @Date 2023/7/29 14:48
 * @Description 订单应用服务
 * @Version 1.0
 */
@Service
@DubboService
public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Autowired
    private ComfirmOrderDomainServiceImpl comfirmOrderDomainService;
    @Autowired
    private DeliveryAddressService deliveryAddressDomainService;
    @Autowired
    private CartService cartService;
    @Resource
    private IdRepository idRepository;

    @Override
    public ComfirmOrderBO placeOrderWithCart(PlaceOrderWithCartDTO placeOrderDTO) {
        Customer customer = Customer.mock();
        List<Cart> carts = cartService.findCarts(customer, placeOrderDTO.getCartItemIds());
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findDefault(customer);
        List<Product> products=extractProductItem(placeOrderDTO);
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(customer, products, deliveryAddress);
        return new ComfirmOrderBO(comfirmOrder, deliveryAddress);
    }

    @Override
    public ComfirmOrderBO placeOrderWithProduct(PlaceOrderWithPrductDTO placeOrderDTO) {
        Customer customer = Customer.mock();
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findDefault(customer);
        Product product=extractProductItem(placeOrderDTO);
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(customer, Arrays.asList(product), deliveryAddress);
        return new ComfirmOrderBO(comfirmOrder, deliveryAddress);
    }

    private List<Product>  extractProductItem(PlaceOrderWithCartDTO placeOrderDTO){
        return null;
    }

    private Product extractProductItem(PlaceOrderWithPrductDTO placeOrderDTO){
        return null;
    }


}
