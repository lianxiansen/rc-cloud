package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.CartId;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.comfirmorder.ComfirmOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.DeliveryType;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.TradeType;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseRedisUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @ClassName ComfirmOrderRepositoryUnitTest
 * @Author liandy
 * @Date 2023/8/4 08:33
 * @Description 确认订单资源库单元测试
 * @Version 1.0
 */
@DisplayName("确认订单资源库单元测试")
@Import({LocalIdRepositoryImpl.class, ComfirmOrderRepositoryImpl.class})
public class ComfirmOrderRepositoryUnitTest extends BaseRedisUnitTest {
    @Resource
    private ComfirmOrderRepository comfirmOrderRepository;
    @Resource
    private IdRepository idRepository;
    private ComfirmOrder comfirmOrder;

    @BeforeEach
    public void beforeEach() {
        comfirmOrder = new ComfirmOrder(idRepository.nextId());
        comfirmOrder.addLine(new ComfirmOrderLine(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()), Product.mockProductA1(),new ProductQuality(1)));
        comfirmOrder.addLine(new ComfirmOrderLine(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()), Product.mockProductA2(),new ProductQuality(1)));
        comfirmOrder.addLine(new ComfirmOrderLine(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()), Product.mockProductB1(),new ProductQuality(1)));
        comfirmOrder.addLine(new ComfirmOrderLine(idRepository.nextId(), comfirmOrder.getId(), new CartId(idRepository.nextId()), Product.mockProductB1(),new ProductQuality(1)));
        comfirmOrder.changeTradeType(TradeType.OFFLINE);
        comfirmOrder.changeDeliveryType(DeliveryType.CONSIGN);
        comfirmOrder.setFreightAmount(BigDecimal.ZERO);
        comfirmOrderRepository.save(comfirmOrder);
    }


    @Test
    public void save() {
        boolean saved = comfirmOrderRepository.save(comfirmOrder);
        Assertions.assertTrue(saved);
    }
    @Test
    public void findById() {
        ComfirmOrder comfirmOrderDB = comfirmOrderRepository.findById(comfirmOrder.getId());
        Assertions.assertTrue(Objects.nonNull(comfirmOrderDB));
    }
}
