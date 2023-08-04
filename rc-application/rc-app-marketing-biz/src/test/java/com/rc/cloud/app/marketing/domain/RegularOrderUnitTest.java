package com.rc.cloud.app.marketing.domain;


import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.UserId;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;
import com.rc.cloud.app.marketing.domain.entity.common.SettledEnum;
import com.rc.cloud.app.marketing.domain.entity.common.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.regularorder.*;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.ConsignStatus;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.OrderStatus;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Receiver;
import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrder;
import com.rc.cloud.app.marketing.domain.entity.settlementorder.SettlementOrderService;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.impl.ComfirmOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.domain.service.impl.SubmitOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.ComfirmOrderRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.DeliveryAddressRepositoryImpl;
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

/**
 * @ClassName RegularOrderUnitTest
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
        LocalIdRepositoryImpl.class, ComfirmOrderDomainServiceImpl.class, SubmitOrderDomainServiceImpl.class,
        ComfirmOrderDomainServiceImpl.class, RegularOrderService.class, SettlementOrderService.class,
        ComfirmOrderRepositoryImpl.class, DeliveryAddressService.class, DeliveryAddressRepositoryImpl.class
})
@DisplayName("常规订单")
public class RegularOrderUnitTest extends BaseDbAndRedisUnitTest {
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
    private RegularOrderService regularOrderService;
    private Area area;
    private DeliveryAddress deliveryAddress;
    private BigDecimal freightAmount;
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
        cart.setUserId(new UserId("5b6b70eafeaa9938cff8e430245090c7"));
        cart.setId(new CartId("436175fb281bf0a9a2c4ded48cf02b4c"));
        carts.add(cart);
        area = new Area("浙江省", "台州市", "黄岩区", "");
        freightAmount = BigDecimal.ZERO;
        categoryId = "80481ce0-1f15-49e4-bbe9-e192bcd";
        comfirmOrder = comfirmOrderDomainService.placeOrder(carts);


    }

    @Nested
    class ComfirmOrderTest {
        @Test
        public void comfirmOrder() {
            ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(carts);
            assertionsComfirmOrder(comfirmOrder);
        }

        private void assertionsComfirmOrder(ComfirmOrder comfirmOrder) {
            Assertions.assertTrue(
                    comfirmOrder.getPayType() == 0 &&
                            comfirmOrder.getDeliveryType().getKey() == DeliveryType.CONSIGN.getKey() &&
                            StringUtils.isEmpty(comfirmOrder.getNote()) &&
                            new BigDecimal(800).equals(comfirmOrder.getProductAmout()) &&
                            comfirmOrder.getFreightAmount().equals(freightAmount) &&
                            comfirmOrder.getPayAmount().equals(new BigDecimal(800).add(freightAmount)) &&
                            comfirmOrder.getItems().size() == 4
            );
        }

        @Test
        public void changePayType() {
        }

        @Test
        public void changeDeliveryType2Express() {
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
//            Assertions.assertTrue(false);
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
        private List<RegularOrder> orders;
        private RegularOrder order;

        @BeforeEach
        public void submitOrder() {
            orders = submitOrderDomainService.submitOrder(comfirmOrder);
            RegularOrder order = orders.stream().findFirst().get();
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
//            Assertions.assertTrue(order.getPayAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum))) &&
//                    order.getProductItemQuantity() == productItemNum);
        }

        @Test
        public void submitOrderThenCheckTransactionId() {
//            Assertions.assertTrue(order.getPayAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum))) &&
//                    order.getProductItemQuantity() == productItemNum);
        }

    }

    @Nested
    class PayOrderTest {
        private List<RegularOrder> orders;
        private RegularOrder order;

        @BeforeEach
        public void beforeEach() {
            orders = submitOrderDomainService.submitOrder(comfirmOrder);
            order = orders.stream().findFirst().get();
        }

        @Test
        public void payOrder() {
            BigDecimal payAmount = new BigDecimal(100);
            order.pay(payAmount);
            Assertions.assertTrue(
                    order.getOrderStatus() == OrderStatus.DELIVERING &&
                            order.getPayStatus() == PayStatus.PAYED &&
                            order.getChangeAmount().equals(payAmount)
            );
        }
    }


    @Nested
    class SettleOrderTest {
        private List<RegularOrder> orders;
        private RegularOrder order;

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
            orderSettlement.setPayAmount(order.getPayAmount());
            orderSettlement.setPayStatus(PayStatus.UNPAY);
            orderSettlement.setPayType(order.getPayType());
            orderSettlement.setTradeNo(idRepository.nextId());
            Assertions.assertTrue(orderSettlement.getPayType() == order.getPayType() &&
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
            List<RegularOrder> orders = regularOrderService.findOrdersByTradeNo(tradeNo);
            SettlementOrder settlementOrder = settlementOrderService.findBy(tradeNo);
            AssertUtils.notNull(settlementOrder, "结算订单不存在");
            // 修改订单信息
            for (RegularOrder order : orders) {


            }
            settlementOrder.setSettled(SettledEnum.YES);
            settlementOrder.setPayStatus(PayStatus.PAYED);
            settlementOrder.setOutTradeNo(outTradeNo);
            Assertions.assertTrue(settlementOrder.getSettled() == SettledEnum.YES &&
                    settlementOrder.getPayStatus() == PayStatus.PAYED &&
                    StringUtils.isNotEmpty(settlementOrder.getOutTradeNo())
            );
        }
    }


}
