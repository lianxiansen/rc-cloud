package com.rc.cloud.app.marketing.infrastructure.repository;

import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.regularorder.*;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.regularorder.valobj.Receiver;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName OrderRepositoryUnitTest
 * @Author liandy
 * @Date 2023/8/4 10:11
 * @Description 订单资源库单元测试
 * @Version 1.0
 */
@DisplayName("订单资源库单元测试")
@Import({LocalIdRepositoryImpl.class, RegularOrderRepositoryImpl.class, OrderService.class})
public class RegularOrderRepositoryUnitTest extends BaseDbAndRedisUnitTest {
    @Resource
    private RegularOrderRepository orderRepository;
    @Resource
    private IdRepository idRepository;
    @Resource
    private OrderService orderService;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void save() {
        //订单
        RegularOrder order = new RegularOrder(idRepository.nextId(), orderService.generateOrderSn("13857652343"));
        order.setBuyer(new Buyer(idRepository.nextId(), RandomUtil.randomString(8), RandomUtil.randomString(8), RandomUtil.randomNumbers(8)));
        order.setReceiver(new Receiver(RandomUtil.randomString(8), RandomUtil.randomString(8), RandomUtil.randomString(8)));
        //订单项
        RegularOrderItem orderItem = new RegularOrderItem(idRepository.nextId(), order.getId());
        orderItem.setProduct(Product.mockProductA());
        orderItem.setProductItem(ProductItem.mockProductItemA1());
        order.addItem(orderItem);

        orderItem = new RegularOrderItem(idRepository.nextId(), order.getId());
        orderItem.setProduct(Product.mockProductA());
        orderItem.setProductItem(ProductItem.mockProductItemA1());
        order.addItem(orderItem);
        //计算运费
        order.setFreightAmount(new BigDecimal(100));
        boolean saved = orderRepository.save(order);
        Assertions.assertTrue(saved);
    }
}
