package com.rc.cloud.app.marketing;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.SettledEnum;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.order.Order;
import com.rc.cloud.app.marketing.domain.entity.order.*;
import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrder;
import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrderService;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.impl.ComfirmOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.domain.service.impl.SubmitOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName OrderTest
 * @Author liandy
 * @Date 2023/7/27 13:42
 * @Description
 * @Version 1.0
 * 1.确认订单
 * 1.1修改收货地址
 * 1.2修改交易方式
 * 1.3修改配送方式
 * 1.4订单备注
 * <p>
 * 2.提交订单
 * 2.1获取库存
 * 2.2提交订单
 * 2.3订单结算
 * 2.4删除购物车
 * 2.5更新库存
 * <p>
 * 3.订单支付
 * <p>
 * 4.订单结算
 * <p>
 * 5.发货
 * <p>
 * 6.确认收货
 * <p>
 * 7.修改订单
 */
@Import({
        LocalIdRepositoryImpl.class, ComfirmOrderDomainServiceImpl.class, SubmitOrderDomainServiceImpl.class, ComfirmOrderDomainServiceImpl.class
})
@DisplayName("订单")
public class OrderTest extends BaseDbAndRedisUnitTest {
    private ComfirmOrder comfirmOrder;
    private String cartId;
    private List<Cart> carts;
    @Autowired
    private ComfirmOrderDomainService comfirmOrderDomainService;
    @Autowired
    private SubmitOrderDomainService submitOrderDomainService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private OrderService orderService;
    private Area area;
    private DeliveryAddress deliveryAddress;
    private BigDecimal freightAmount;
    private Product product;
    private String productItemId;
    private BigDecimal productItemPrice;
    private String productItemAttribute;
    int productItemNum;
    @Autowired
    private SettlementOrderService settlementOrderService;
    /**
     * 分类标识
     */
    private String categoryId;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void beforeEach() {
        carts = new ArrayList<>();
        Cart cart = new Cart();
        carts.add(cart);
        carts.add(cart);
        area = new Area("浙江省", "台州市", "黄岩区", "");
        deliveryAddress = new DeliveryAddress("445be69b-11df-4cf8-80a3-2b7beb5", "test", "13800001234", "10068", area);
        freightAmount = BigDecimal.ZERO;
        categoryId = "80481ce0-1f15-49e4-bbe9-e192bcd";
        String productId = "7747a149-5a79-4e37-8e42-061e434";
        String productName = "单杯调味罐";
        String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        String productArticleNo = "56226";
        product = new Product(productId, productName, productImage, productArticleNo);


        productItemId = "5a02de05-29ec-4cd0-acef-7f57dbe";
        productItemPrice = new BigDecimal(7.80);
        productItemAttribute = "48个/箱";
        productItemNum = 2;
        comfirmOrder = comfirmOrderDomainService.placeOrder(carts);


    }

    @Nested
    class ComfirmOrderTest {
        @Test
        public void comfirmOrder() {
            ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(carts);
            redisTemplate.opsForValue().set("key", JSONObject.toJSONString(comfirmOrder));
            String res = (String) redisTemplate.opsForValue().get("key");
            comfirmOrderAssertions(comfirmOrder);
        }

        private void comfirmOrderAssertions(ComfirmOrder comfirmOrder) {
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
        public void modifyOrder() {
            Assertions.assertTrue(false);
        }
    }

    /**
     * 2.提交订单
     * 2.1获取库存
     * 2.2提交订单
     * 2.3订单结算
     * 2.4删除购物车
     * 2.5更新库存
     */
    @Nested
    class SubmitOrderTest {
        private List<Order> orders;
        private Order order;

        @BeforeEach
        public void submitOrder() {
            orders = submitOrderDomainService.submitOrder(comfirmOrder);
            Order order = orders.stream().findFirst().get();
        }


        @Test
        public void submitOrderThenCheckBaseState() {
            Assertions.assertTrue(Objects.nonNull(order.getId()) &&
                    Objects.nonNull(order.getOrderNumber()) &&
                    order.getOrderStatus() == OrderStatus.AUDITING &&
                    order.getPayType() == 0 &&
                    order.getPayStatus() == PayStatus.UNPAY &&
                    order.getConsignStatus() == ConsignStatus.UNCONSIGN
            );
        }

        @Test
        public void submitOrderThenCheckBuyer() {
            Buyer buyer = new Buyer("123", "陈激扬", "去11", "18258687039");
            Assertions.assertEquals(buyer, order.getBuyer());
        }

        @Test
        public void submitOrderThenCheckReceiver() {
            Receiver receiver = new Receiver("某某某", "浙江省台州市黄岩区王西路41号", "13812345678");
            Assertions.assertEquals(receiver, order.getReceiver());
        }

        @Test
        public void submitOrderThenCheckTotalAmountAndNum() {
            Assertions.assertTrue(order.getPayAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum))) &&
                    order.getTotalNum() == productItemNum);
        }

        @Test
        public void submitOrderThenCheckTransactionId() {
            Assertions.assertTrue(order.getPayAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum))) &&
                    order.getTotalNum() == productItemNum);
        }

    }

    @Nested
    class PayOrderTest {
        private List<Order> orders;
        private Order order;

        @BeforeEach
        public void beforeEach() {
            orders = submitOrderDomainService.submitOrder(comfirmOrder);
            order = orders.stream().findFirst().get();
        }

        @Test
        public void payOrder() {
            String transactionId = "1000320306201511078440737890";
            BigDecimal payAmount = new BigDecimal(100);
            order.pay(transactionId, payAmount);
            Assertions.assertTrue(
                    order.getOrderStatus() == OrderStatus.DELIVERING &&
                            order.getPayStatus() == PayStatus.PAYED &&
                            order.getTransactionId() == transactionId &&
                            order.getActualPayAmount().equals(payAmount)
            );
        }
    }


    @Nested
    class SettleOrderTest {
        private List<Order> orders;
        private Order order;

        @BeforeEach
        public void beforeEach() {
            orders = submitOrderDomainService.submitOrder(comfirmOrder);
            order = orders.stream().findFirst().get();
        }

        @Test
        public void processSettleBefore() {
            LocalDateTime createTime = LocalDateTime.now();
            SettlementOrder orderSettlement = new SettlementOrder();
            orderSettlement.setBuyerId(order.getBuyer().getBuyerId());
            orderSettlement.setSettled(SettledEnum.NO);
            orderSettlement.setCreateTime(createTime);
            orderSettlement.setOrderNumber(order.getOrderNumber());
            orderSettlement.setPayAmount(order.getPayAmount());
            orderSettlement.setPayStatus(PayStatus.UNPAY);
            orderSettlement.setPayType(order.getPayType());
            orderSettlement.setTradeNo(idRepository.nextId());
            Assertions.assertTrue(orderSettlement.getOrderNumber().equals(order.getOrderNumber()) &&
                    orderSettlement.getPayType() == order.getPayType() &&
                    orderSettlement.getPayAmount().equals(order.getPayAmount()) &&
                    orderSettlement.getBuyerId().equals(order.getBuyer().getBuyerId()) &&
                    orderSettlement.getSettled() == SettledEnum.NO &&
                    orderSettlement.getPayAmount().equals(order.getPayAmount()) &&
                    orderSettlement.getPayStatus() == PayStatus.UNPAY &&
                    orderSettlement.getPayType() == order.getPayType() &&
                    StringUtils.isNotEmpty(orderSettlement.getTradeNo())
            );
        }
        @Test
        public void settle() {
            String tradeNo = "1217752501201407033233368018";
            String outTradeNo = "1217752501201407033233368018";
            List<Order> orders = orderService.findOrdersByTradeNo(tradeNo);
            List<String> orderNumbers = orders.stream().map(order -> order.getOrderNumber()).collect(Collectors.toList());
            List<SettlementOrder> orderSettlements = settlementOrderService.findList(orderNumbers);
            // 修改订单信息
            for (Order order : orders) {
                SettlementOrder orderSettlement = orderSettlements.stream()
                        .filter(p -> p.getOrderNumber().equals(order.getOrderNumber()))
                        .findFirst().get();
                AssertUtils.notNull(orderSettlement, "结算订单不存在");
                orderSettlement.setSettled(SettledEnum.YES);
                orderSettlement.setPayStatus(PayStatus.PAYED);
                orderSettlement.setOutTradeNo(outTradeNo);
                Assertions.assertTrue(orderSettlement.getSettled() == SettledEnum.YES &&
                        orderSettlement.getPayStatus() == PayStatus.PAYED &&
                        StringUtils.isNotEmpty(orderSettlement.getOutTradeNo())
                );
            }
        }
    }


}
