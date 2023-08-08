package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderItem;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Service;

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
    public ComfirmOrder placeOrder(Customer customer, List<Product> products, DeliveryAddress deliveryAddress) {
        ComfirmOrder comfirmOrder = createComfirmOrder(products,deliveryAddress);
        comfirmOrderRepository.save(comfirmOrder);
        return comfirmOrder;
    }

    private ComfirmOrder createComfirmOrder(List<Product> products,DeliveryAddress deliveryAddress) {
        ComfirmOrder comfirmOrder = new ComfirmOrder(idRepository.nextId());
        comfirmOrder.setPayType(0);
        comfirmOrder.setDeliveryType(DeliveryType.CONSIGN);
        comfirmOrder.setFreightAmount(BigDecimal.ZERO);
        extractProductToComfirmOrderItem(products,comfirmOrder);
        return comfirmOrder;
    }

    private void extractProductToComfirmOrderItem(List<Product> products,ComfirmOrder comfirmOrder) {
        products.forEach(product->{
            comfirmOrder.addItem(new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()),product));

        });

    }


}
