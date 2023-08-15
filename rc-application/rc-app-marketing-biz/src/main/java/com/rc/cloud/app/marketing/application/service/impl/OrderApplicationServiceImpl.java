package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.app.marketing.application.bo.ComfirmOrderBO;
import com.rc.cloud.app.marketing.application.bo.RegularOrderBO;
import com.rc.cloud.app.marketing.application.dto.ComfirmOrderSubmitDTO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithPrductDTO;
import com.rc.cloud.app.marketing.application.service.OrderApplicationService;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.CartService;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.customer.CustomerRepository;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.TradeType;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.impl.ComfirmOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.infrastructure.config.constant.OrderErrorCodeConstant;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Resource
    private ComfirmOrderDomainServiceImpl comfirmOrderDomainService;
    @Resource
    private DeliveryAddressService deliveryAddressDomainService;
    @Resource
    private CartService cartService;
    @Resource
    private ComfirmOrderRepository comfirmOrderRepository;
    @Resource
    private SubmitOrderDomainService submitOrderDomainService;
    @Resource
    private CustomerRepository customerRepository;
    @Override
    public ComfirmOrderBO placeOrderWithCart(PlaceOrderWithCartDTO placeOrderDTO) {
        Customer customer = customerRepository.getCustomer();
        List<Cart> carts = cartService.findCarts(new UserId(customer.getId()),placeOrderDTO.getCartIds());
        if (CollectionUtils.isEmpty(carts)) {
            throw new ServiceException(OrderErrorCodeConstant.PLACE_ORDER_WHEN_CART_EMPTY);
        }
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findDefault(customer);
        Map<Product, ProductQuality> products = extractProduct(carts);
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(customer, products, deliveryAddress);
        return new ComfirmOrderBO(deliveryAddress, comfirmOrder, TradeType.values(), DeliveryType.values());
    }

    @Override
    public ComfirmOrderBO placeOrderWithProduct(PlaceOrderWithPrductDTO placeOrderDTO) {
        Customer customer = customerRepository.getCustomer();
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findDefault(customer);
        Product product = new Product(placeOrderDTO.getProductId(), placeOrderDTO.getProductName(), placeOrderDTO.getProductImage(), placeOrderDTO.getProductArticleNo(), placeOrderDTO.getProductAttribute(), placeOrderDTO.getProductPrice());
        ProductQuality quality = new ProductQuality(placeOrderDTO.getQuantity());
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(customer, product, quality, deliveryAddress);
        return new ComfirmOrderBO(deliveryAddress, comfirmOrder, TradeType.values(), DeliveryType.values());
    }

    @Override
    public List<RegularOrderBO> submitComfirmOrder(ComfirmOrderSubmitDTO dto) {
        Customer customer = customerRepository.getCustomer();
        validateComfirmOrderSubmitDTO(dto);
        ComfirmOrder comfirmOrder = findComfirmOrderAndCheckNotNull(dto.getComfirmOrderId());
        checkDeliveryAddressNotNull(dto);
        changeComfirmOrder(dto, comfirmOrder);
        List<RegularOrder> regularOrders = submitOrderDomainService.submitOrder(customer, comfirmOrder);
        List<CartId> cartIds = comfirmOrder.getLines().stream().map(line -> line.getCartId()).collect(Collectors.toList());
        cartService.deleteBatch(cartIds);
        return null;
    }




    /**
     * @param carts
     * @return
     */
    private Map<Product, ProductQuality> extractProduct(List<Cart> carts) {
        Map<Product, ProductQuality> products = new HashMap<>();
        carts.forEach(cart -> {
            Product product = new Product(cart.getCartProductDetail().getId().id(), cart.getCartProductDetail().getName(),
                    cart.getCartProductSkuDetail().getSkuImage(), cart.getCartProductDetail().getSpuCode(), cart.getCartProductSkuDetail().stkAttributesToString(), cart.getCartProductSkuDetail().getPrice());
            ProductQuality quality = new ProductQuality(cart.getNum());
            products.put(product, quality);
        });
        return products;
    }


    private void validateComfirmOrderSubmitDTO(ComfirmOrderSubmitDTO dto) {
        if (StringUtils.isEmpty(dto.getComfirmOrderId())) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_COMFIRM_ORDER_ID_IS_EMPTY);
        }
        if (StringUtils.isEmpty(dto.getDeliveryAddressId())) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_ADDRESS_ID_IS_EMPTY);
        }
        if (Objects.isNull(dto.getTradeType())) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_TRADE_TYPE_IS_EMPTY);
        }
        if (Objects.isNull(dto.getDeliveryType())) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_TYPE_IS_EMPTY);
        }
    }

    private void changeComfirmOrder(ComfirmOrderSubmitDTO dto, ComfirmOrder comfirmOrder) {
        TradeType tradeType = TradeType.valueOf(dto.getTradeType());
        if (Objects.isNull(tradeType)) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_TRADE_TYPE_IS_NULL);
        }
        comfirmOrder.changeTradeType(tradeType);
        DeliveryType deliveryType = DeliveryType.valueOf(dto.getDeliveryType());
        if (Objects.isNull(deliveryType)) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_TYPE_IS_NULL);
        }
        comfirmOrder.changeDeliveryType(deliveryType);
    }
    private void checkDeliveryAddressNotNull(ComfirmOrderSubmitDTO dto) {
        DeliveryAddress deliveryAddress = deliveryAddressDomainService.findById(dto.getDeliveryAddressId());
        if (Objects.isNull(deliveryAddress)) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_ADDRESS_IS_NULL);
        }
    }

    private ComfirmOrder findComfirmOrderAndCheckNotNull(String comfirmOrderId) {
        ComfirmOrder comfirmOrder = comfirmOrderRepository.findById(comfirmOrderId);
        if (Objects.isNull(comfirmOrder)) {
            throw new ServiceException(OrderErrorCodeConstant.SUBMIT_COMFIRM_ORDER_WHEN_COMFIRM_ORDER_IS_NULL);
        }
        return comfirmOrder;
    }
}
