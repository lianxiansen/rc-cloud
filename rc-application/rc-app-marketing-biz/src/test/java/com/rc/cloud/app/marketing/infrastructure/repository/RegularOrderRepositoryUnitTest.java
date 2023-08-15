package com.rc.cloud.app.marketing.infrastructure.repository;

import com.rc.cloud.app.marketing.domain.entity.order.valobj.Product;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderLine;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderRepository;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderService;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Buyer;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.ProductQuality;
import com.rc.cloud.app.marketing.domain.entity.order.valobj.Receiver;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderRepositoryUnitTest
 * @Author liandy
 * @Date 2023/8/4 10:11
 * @Description 订单资源库单元测试
 * @Version 1.0
 */
@DisplayName("订单资源库单元测试")
@Import({LocalIdRepositoryImpl.class, RegularOrderRepositoryImpl.class, RegularOrderService.class})
public class RegularOrderRepositoryUnitTest extends BaseDbAndRedisUnitTest {
    @Resource
    private RegularOrderRepository regularOrderRepository;
    @Resource
    private IdRepository idRepository;

    @Resource
    private RegularOrderService regularOrderService;

    private   RegularOrder regularOrder;
    @BeforeEach
    public void beforeEach() {
        String orderNo = regularOrderService.generateOrderSn();
        String regularId = idRepository.nextId();
        regularOrder=new RegularOrder(regularId,orderNo);
        regularOrder.setBuyer(Buyer.mockBuyer());
        regularOrder.setReceiver(Receiver.mockReceiver());
        regularOrder.setTradeNo(idRepository.nextId());
        List<RegularOrderLine> lines=new ArrayList<>();
        RegularOrderLine line1 = new RegularOrderLine(idRepository.nextId(),regularId, Product.mockProductA1(),new ProductQuality(1));
        RegularOrderLine line2 = new RegularOrderLine(idRepository.nextId(),regularId, Product.mockProductA2(),new ProductQuality(1));
        lines.add(line1);
        lines.add(line2);
        regularOrderRepository.save(regularOrder);
    }

    @Test
    public void save() {
        boolean saved=regularOrderRepository.save(regularOrder);
        Assertions.assertTrue(saved);
    }
    @Test
    public void findById(){
        RegularOrder regularOrderActual=regularOrderRepository.findById(regularOrder.getId());
        Assertions.assertTrue(regularOrder.equals(regularOrderActual));
    }
}
