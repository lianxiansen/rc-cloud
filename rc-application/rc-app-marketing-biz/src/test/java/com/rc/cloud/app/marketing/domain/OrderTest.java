package com.rc.cloud.app.marketing.domain;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.app.marketing.domain.entity.cartmock.Cart;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderService;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.*;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderService;
import com.rc.cloud.app.marketing.domain.service.impl.SubmitOrderServiceImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName OrderTest
 * @Author liandy
 * @Date 2023/7/27 13:42
 * @Description
 * @Version 1.0
 * 1.下订单
 * 1.1修改收货地址
 * 1.2修改交易方式
 * 1.3修改配送方式
 * 1.4订单备注
 * 2.提交订单
 * 3.订单支付
 * 4.发货
 * 5.确认收货
 * 6.修改订单
 */
@Import({
        LocalIdRepositoryImpl.class, ComfirmOrderService.class, SubmitOrderServiceImpl.class
})
@DisplayName("订单")
public class OrderTest extends BaseDbAndRedisUnitTest {
    private ComfirmOrder comfirmOrder;
    private String cartId;
    private List<Cart> carts;
    @Autowired
    private ComfirmOrderService comfirmOrderService;
    @Autowired
    private SubmitOrderService submitOrderService;
    @Resource
    private IdRepository idRepository;
    private Area area;
    private DeliveryAddress deliveryAddress;
    private BigDecimal freightAmount;
    private String productId;
    private String productName;
    private String productImage;
    private String productArticleNo;
    private String productItemId;
    private BigDecimal productItemPrice;
    private String productItemAttribute;
    int productItemNum;
    /**
     * 分类标识
     */
    private String categoryId;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void beforeEach() {
        carts = new ArrayList<>();
        carts.add(new Cart("B357149-5a79-4e37-8e42-061e434"));

        area = new Area("浙江省", "台州市", "黄岩区", "");
        deliveryAddress = new DeliveryAddress("445be69b-11df-4cf8-80a3-2b7beb5", "test", "13800001234", "10068", area);
        freightAmount = BigDecimal.ZERO;
        categoryId = "80481ce0-1f15-49e4-bbe9-e192bcd";
        productId = "7747a149-5a79-4e37-8e42-061e434";
        productName = "单杯调味罐";
        productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        productArticleNo = "56226";
        productItemId = "5a02de05-29ec-4cd0-acef-7f57dbe";
        productItemPrice = new BigDecimal(7.80);
        productItemAttribute = "48个/箱";
        productItemNum = 2;
        comfirmOrder = comfirmOrderService.placeOrder(carts);
    }

    @Test
    public void placeOrder() {
        ComfirmOrder comfirmOrder = comfirmOrderService.placeOrder(carts);
        redisTemplate.opsForValue().set("key", JSONObject.toJSONString(comfirmOrder));
        String res = (String) redisTemplate.opsForValue().get("key");
        placeOrderAssertions(comfirmOrder);
    }

    private void placeOrderAssertions(ComfirmOrder comfirmOrder) {
        Assertions.assertTrue(comfirmOrder.getDeliveryAddressId().equals(deliveryAddress.getId()) &&
                comfirmOrder.getPayType() == 0 &&
                comfirmOrder.getDeliveryType().getKey() == DeliveryType.CONSIGN.getKey() &&
                StringUtils.isEmpty(comfirmOrder.getNote()) &&
                productItemPrice.multiply(new BigDecimal(productItemNum)).equals(comfirmOrder.getProductAmout()) &&
                comfirmOrder.getFreightAmount().equals(freightAmount) &&
                comfirmOrder.getPayAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum)).add(freightAmount))
        );
    }

    @Test
    public void changePayType() {
        Assertions.assertTrue(false);
    }

    @Test
    public void changeDeliveryTypeWhenDeliveryTypeEqualsExpress() {
        DeliveryType deliveryType = DeliveryType.EXPRESS;
        comfirmOrder.changeDeliveryType(deliveryType);
        Assertions.assertEquals(comfirmOrder.getDeliveryType().getKey(), deliveryType.getKey());
    }

    @Test
    public void changeDeliveryTypeWhenDeliveryTypeEqualsHome() {
        DeliveryType deliveryType = DeliveryType.HOME;
        comfirmOrder.changeDeliveryType(deliveryType);
        Assertions.assertEquals(comfirmOrder.getDeliveryType().getKey(), deliveryType.getKey());
    }

    @Test
    public void modifyNote() {
        String note = RandomUtil.randomString(32);
        comfirmOrder.modifyNote(note);
        Assertions.assertEquals(note, comfirmOrder.getNote());
    }

    @Test
    public void submitComfirmOrderThenCheckBaseState() {
        Order order = submitOrderService.createOrder(comfirmOrder);
        Assertions.assertTrue(Objects.nonNull(order.getId()) &&
                Objects.nonNull(order.getOrderNo()) &&
                order.getOrderStatus() == OrderStatus.AUDITING &&
                order.getPayType() == 0 &&
                order.getPayStatus() == PayStatus.UNPAY &&
                order.getConsignStatus() == ConsignStatus.UNCONSIGN
        );
    }

    @Test
    public void submitComfirmOrderThenCheckBuyer() {
        Buyer buyer = new Buyer("陈激扬", "去11", "18258687039");
        Order order = submitOrderService.createOrder(comfirmOrder);
        Assertions.assertEquals(buyer, order.getBuyer());
    }

    @Test
    public void submitComfirmOrderThenCheckReceiver() {
        Receiver receiver = new Receiver("某某某", "浙江省台州市黄岩区王西路41号", "13812345678");
        Order order = submitOrderService.createOrder(comfirmOrder);
        Assertions.assertEquals(receiver, order.getReceiver());
    }

    @Test
    public void submitComfirmOrderThenCheckTotalAmountAndNum() {
        Order order = submitOrderService.createOrder(comfirmOrder);
        Assertions.assertTrue(order.getTotalAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum))) &&
                order.getTotalNum() == productItemNum);
    }


    @Test
    public void payOrder() {
        String transactionId = "1000320306201511078440737890";
        BigDecimal payAmount = new BigDecimal(100);
        Order order = submitOrderService.createOrder(comfirmOrder);
        order.pay(transactionId, payAmount);
        Assertions.assertTrue(
                order.getOrderStatus() == OrderStatus.DELIVERING &&
                        order.getPayStatus() == PayStatus.PAYED &&
                        order.getTransactionId() == transactionId &&
                        order.getPayAmount().equals(payAmount)
        );
    }

    @Test
    public void modifyOrder() {
        Assertions.assertTrue(false);
    }
}
