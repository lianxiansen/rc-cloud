package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    public ComfirmOrder placeOrder(Customer customer, Product product, ProductQuality quality, DeliveryAddress deliveryAddress) {
        Map<Product, ProductQuality> products=new HashMap<>();
        products.put(product,quality);
        return placeOrder(customer, products, deliveryAddress);
    }

    @Override
    public ComfirmOrder placeOrder(Customer customer, Map<Product, ProductQuality> products, DeliveryAddress deliveryAddress) {
        ComfirmOrder comfirmOrder = createComfirmOrder(products, deliveryAddress);
        comfirmOrderRepository.save(comfirmOrder);
        return comfirmOrder;
    }

    private ComfirmOrder createComfirmOrder(Map<Product, ProductQuality> products, DeliveryAddress deliveryAddress) {
        ComfirmOrder comfirmOrder = new ComfirmOrder(idRepository.nextId());
        comfirmOrder.changeDeliveryType(DeliveryType.CONSIGN);
        comfirmOrder.setFreightAmount(BigDecimal.ZERO);
        extractProductToComfirmOrderLine(products, comfirmOrder);
        return comfirmOrder;
    }

    private void extractProductToComfirmOrderLine(Map<Product, ProductQuality> products, ComfirmOrder comfirmOrder) {
        products.entrySet().forEach(entry -> {
            Product product=entry.getKey();
            ProductQuality quality=entry.getValue();
            comfirmOrder.addLine(new ComfirmOrderLine(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()), product, quality));
        });

    }


}
