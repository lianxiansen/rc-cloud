package com.rc.cloud.app.marketing.domain;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.rc.cloud.app.marketing.domain.cart.CartItem;
import com.rc.cloud.app.marketing.domain.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.comfirmorder.ComfirmOrderDomainService;
import com.rc.cloud.app.marketing.domain.comfirmorder.DeliveryType;
import com.rc.cloud.app.marketing.domain.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.order.*;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.redis.config.RcRedisAutoConfiguration;
import com.rc.cloud.common.test.config.RedisTestConfiguration;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
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
 */
@Import({
        LocalIdRepositoryImpl.class, ComfirmOrderDomainService.class,
        // Redis 配置类
        RedisTestConfiguration.class, // Redis 测试配置类，用于启动 RedisServer
        RedisAutoConfiguration.class, // Spring Redis 自动配置类
        RcRedisAutoConfiguration.class, // 自己的 Redis 配置类
        RedissonAutoConfiguration.class, // Redisson 自动高配置类
})
@DisplayName("订单")
public class OrderTest extends BaseDbAndRedisUnitTest{
    private ComfirmOrder comfirmOrder;
    private String cartId;
    private List<CartItem> cartItems;
    @Autowired
    private ComfirmOrderDomainService comfirmOrderDomainService;
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
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("B357149-5a79-4e37-8e42-061e434"));

        area = new Area("浙江省", "台州市", "黄岩区", "");
        deliveryAddress = new DeliveryAddress("445be69b-11df-4cf8-80a3-2b7beb5", "test", "13800001234", "10068", area);
        freightAmount = BigDecimal.ZERO;
        categoryId="80481ce0-1f15-49e4-bbe9-e192bcd";
        productId = "7747a149-5a79-4e37-8e42-061e434";
        productName = "单杯调味罐";
        productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
        productArticleNo = "56226";
        productItemId = "5a02de05-29ec-4cd0-acef-7f57dbe";
        productItemPrice = new BigDecimal(7.80);
        productItemAttribute = "48个/箱";
        productItemNum = 2;
        comfirmOrder = comfirmOrderDomainService.placeOrder(cartItems);
    }

    @Test
    public void placeOrder() {
        ComfirmOrder comfirmOrder = comfirmOrderDomainService.placeOrder(cartItems);
        redisTemplate.opsForValue().set("key", JSONObject.toJSONString(comfirmOrder));
        String res= (String) redisTemplate.opsForValue().get("key");
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
        Order order = comfirmOrderDomainService.submit(comfirmOrder);
        Assertions.assertTrue(Objects.nonNull(order.getId())&&
                Objects.nonNull(order.getOrderNo())&&
                order.getOrderStatus()==OrderStatus.AUDITING&&
                order.getPayType()==0&&
                order.getPayStatus()== PayStatus.UNPAY&&
                order.getConsignStatus()==ConsignStatus.UNCONSIGN
        );
    }
    @Test
    public void submitComfirmOrderThenCheckBuyer() {
        Buyer buyer = new Buyer("陈激扬", "去11", "18258687039");
        Order order = comfirmOrderDomainService.submit(comfirmOrder);
        Assertions.assertEquals(buyer, order.getBuyer());
    }

    @Test
    public void submitComfirmOrderThenCheckReceiver() {
        Receiver receiver = new Receiver("某某某", "浙江省台州市黄岩区王西路41号", "13812345678");
        Order order = comfirmOrderDomainService.submit(comfirmOrder);
        Assertions.assertEquals(receiver, order.getReceiver());
    }
    @Test
    public void submitComfirmOrderThenCheckTotalAmountAndNum() {
        Order order = comfirmOrderDomainService.submit(comfirmOrder);
        Assertions.assertTrue(order.getTotalAmount().equals(productItemPrice.multiply(new BigDecimal(productItemNum)))&&
                order.getTotalNum()== productItemNum);
    }

}
