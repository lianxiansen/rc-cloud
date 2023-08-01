package com.rc.cloud.app.marketing.domain.comfirmorder;

import com.rc.cloud.app.marketing.domain.cart.Cart;
import com.rc.cloud.app.marketing.domain.common.ProductItem;
import com.rc.cloud.app.marketing.domain.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.order.Buyer;
import com.rc.cloud.app.marketing.domain.order.Order;
import com.rc.cloud.app.marketing.domain.order.OrderItem;
import com.rc.cloud.app.marketing.domain.order.Receiver;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ComfirmOrderDomainService
 * @Author liandy
 * @Date 2023/7/28 13:45
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ComfirmOrderService {
    @Resource
    private IdRepository idRepository;

    public ComfirmOrder placeOrder(List<Cart> cartItems) {
        ComfirmOrder comfirmOrder = new ComfirmOrder(idRepository.nextId());
        Area area = new Area("浙江省", "台州市", "黄岩区", "");
        DeliveryAddress deliveryAddress = new DeliveryAddress("445be69b-11df-4cf8-80a3-2b7beb5", "test", "13800001234", "10068", area);
        comfirmOrder.setDeliveryAddressId(deliveryAddress.getId());

        //确认订单-商品信息
        cartItems.forEach(cartItem -> {
            String productId = "7747a149-5a79-4e37-8e42-061e434";
            String productName = "单杯调味罐";
            String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
            String productArticleNo = "56226";
            ProductItem productItem = new ProductItem().setProductId(productId)
                    .setId("").setAttribute("48个/箱").setSkuId("").setNum(2).setSpuId("").setImage("").setPrice(new BigDecimal(7.80));
            ComfirmOrderItem item = new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(), productId, productName, productImage, productArticleNo, productItem);
            comfirmOrder.addItem(item);
        });
        return comfirmOrder;
    }

    public Order submit(ComfirmOrder comfirmOrder) {
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
