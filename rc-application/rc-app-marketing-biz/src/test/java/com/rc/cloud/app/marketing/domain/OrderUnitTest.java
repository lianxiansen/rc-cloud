package com.rc.cloud.app.marketing.domain;


import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj.AuditAction;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj.OrderStatus;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.*;
import com.rc.cloud.app.marketing.domain.entity.order.settlementorder.SettledEnum;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderService;
import com.rc.cloud.app.marketing.domain.entity.order.settlementorder.SettlementOrder;
import com.rc.cloud.app.marketing.domain.entity.order.settlementorder.SettlementOrderService;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.SubmitOrderDomainService;
import com.rc.cloud.app.marketing.domain.service.impl.ComfirmOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.domain.service.impl.SubmitOrderDomainServiceImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.ComfirmOrderRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.DeliveryAddressRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.RegularOrderRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 2.6订单改价
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
        ComfirmOrderRepositoryImpl.class, DeliveryAddressService.class, DeliveryAddressRepositoryImpl.class,
        RegularOrderRepositoryImpl.class
})
@DisplayName("订单")
public class OrderUnitTest extends BaseDbAndRedisUnitTest {
    private ComfirmOrder comfirmOrder;
    @MockBean
    private ComfirmOrderRepository comfirmOrderRepository;
    @MockBean
    private RegularOrderRepository regularOrderRepository;
    @Autowired
    private ComfirmOrderDomainService comfirmOrderDomainService;
    @Autowired
    private SubmitOrderDomainService submitOrderDomainService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private RegularOrderService regularOrderService;
    @Autowired
    private SettlementOrderService settlementOrderService;

    /**
     * 分类标识
     */
    private Customer customer;
    private DeliveryAddress deliveryAddress;
    private Map<Product, ProductQuality> products;

    @BeforeEach
    public void beforeEach() {
        customer = Customer.mock();
        deliveryAddress = null;
        products=new HashMap<>();
        products.put(Product.mockProductA1(), new ProductQuality(1));
        products.put(Product.mockProductA2(), new ProductQuality(1));
        products.put(Product.mockProductB1(), new ProductQuality(1));
        products.put(Product.mockProductB2(), new ProductQuality(1));
        comfirmOrder = comfirmOrderDomainService.placeOrder(Customer.mock(), products, deliveryAddress);
    }

    @Nested
    public class comfirmOrder {


        @DisplayName("下单")
        @Test
        public void placeOrder() {
            assertComfirmOrder(comfirmOrder);
        }

        private void assertComfirmOrder(ComfirmOrder comfirmOrder) {
            Assertions.assertTrue(
                    comfirmOrder.getTradeType() == TradeType.ONLINE &&
                            comfirmOrder.getDeliveryType().getCode() == DeliveryType.CONSIGN.getCode() &&
                            StringUtils.isEmpty(comfirmOrder.getNote()) &&
                            new BigDecimal(400).equals(comfirmOrder.getProductAmout()) &&
                            comfirmOrder.getFreightAmount().equals(BigDecimal.ZERO) &&
                            comfirmOrder.getPayAmount().equals(new BigDecimal(400).add(BigDecimal.ZERO)) &&
                            comfirmOrder.getLines().size() == 4
            );
        }

        @Test
        public void changeTradeType() {
        }

        @Test
        public void changeDeliveryType2Express() {
            DeliveryType deliveryType = DeliveryType.EXPRESS;
            comfirmOrder.changeDeliveryType(deliveryType);
            Assertions.assertEquals(comfirmOrder.getDeliveryType().getCode(), deliveryType.getCode());
        }

        @Test
        public void changeDeliveryTypeWhenDeliveryTypeEqualsHome() {
            DeliveryType deliveryType = DeliveryType.HOME;
            comfirmOrder.changeDeliveryType(deliveryType);
            Assertions.assertEquals(comfirmOrder.getDeliveryType().getCode(), deliveryType.getCode());
        }

        @Test
        public void modifyNote() {
            String note = RandomUtil.randomString(32);
            comfirmOrder.modifyNote(note);
            Assertions.assertEquals(note, comfirmOrder.getNote());
        }

    }

    @Nested
    public class RegularOrderTest {
        private List<RegularOrder> orders;
        private RegularOrder order;


        @BeforeEach
        public void beforeEach() {
            orders = submitOrderDomainService.submitOrder(customer, comfirmOrder);
            order = orders.stream().findFirst().get();
        }

        @Test
        public void changeAmountWhenGreaterThanPayAmount() {
            Assertions.assertThrows(ServiceException.class, () -> {
                order.changeAmount(order.getPayAmount().add(BigDecimal.ONE));
            });
        }

        @Test
        public void changeAmountWhenAudited() {
            order.doActoin(new AuditAction());
            Assertions.assertThrows(ServiceException.class, () -> {
                order.changeAmount(order.getPayAmount().subtract(BigDecimal.ONE));
            });
        }

        @Test
        public void submitOrder() {
            Buyer buyer = Buyer.mockBuyer();
            Receiver receiver = Receiver.mockReceiver();
            Assertions.assertTrue(Objects.nonNull(order.getId()) &&
                    Objects.nonNull(order.getOrderNumber()) &&
                    order.getOrderStatus() == OrderStatus.AUDITING &&
                    order.getProductAmount().equals(new BigDecimal(200)) &&
                    order.getFreightAmount().equals(BigDecimal.ZERO) &&
                    Objects.isNull(order.getChangeAmount()) &&
                    order.getTradeType() ==TradeType.ONLINE &&
                    order.getPayStatus() == PayStatus.UNPAY &&
                    order.getConsignStatus() == ConsignStatus.UNCONSIGN &&
                    order.getBuyer().equals(buyer) &&
                    order.getReceiver().equals(receiver) &&
                    Objects.nonNull(order.getTradeNo())
            );
        }

        @Test
        public void payWhenActualPayAmountEqualsOrderPayAmount() {
            BigDecimal payAmount = order.getPayAmount();
            order.pay(payAmount);
            Assertions.assertTrue(
                    order.getOrderStatus() == OrderStatus.DELIVERING &&
                            order.getPayStatus() == PayStatus.PAYED &&
                            order.getPayAmount().equals(payAmount)
            );
        }

        @Test
        public void payWhenActualPayAmountNotEqualsOrderPayAmount() {
            BigDecimal payAmount = order.getPayAmount().add(BigDecimal.ONE);
            Assertions.assertThrows(ServiceException.class, () -> {
                order.pay(payAmount);
            });
        }

    }


    @Nested
    class SettleOrderTest {
        private List<RegularOrder> orders;
        private RegularOrder order;

        @BeforeEach
        public void beforeEach() {
            orders = submitOrderDomainService.submitOrder(customer, comfirmOrder);
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
            orderSettlement.setTradeType(order.getTradeType());
            orderSettlement.setTradeNo(idRepository.nextId());
            Assertions.assertTrue(orderSettlement.getTradeType() == order.getTradeType() &&
                    orderSettlement.getPayAmount().equals(order.getPayAmount()) &&
                    orderSettlement.getBuyerId().equals(order.getBuyer().getBuyerId()) &&
                    orderSettlement.getSettled() == SettledEnum.NO &&
                    orderSettlement.getPayAmount().equals(order.getPayAmount()) &&
                    orderSettlement.getPayStatus() == PayStatus.UNPAY &&
                    orderSettlement.getTradeType() == order.getTradeType() &&
                    StringUtils.isNotEmpty(orderSettlement.getTradeNo())
            );
        }

        @Test
        public void settle() {
            String tradeNo = "1217752501201407033233368018";
            String outTradeNo = "1217752501201407033233368018";
            List<RegularOrder> orders = regularOrderService.findOrdersByTradeNo(tradeNo);
            SettlementOrder settlementOrder = settlementOrderService.findBy(tradeNo);
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
