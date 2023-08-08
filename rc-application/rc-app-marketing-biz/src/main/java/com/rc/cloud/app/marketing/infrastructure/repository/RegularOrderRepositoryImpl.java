package com.rc.cloud.app.marketing.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.regularorder.RegularOrderRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.OrderConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.OrderItemConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.OrderItemMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.RegularOrderMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.OrderItemPO;
import com.rc.cloud.app.marketing.infrastructure.repository.po.RegularOrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName RegularOrderRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:38
 * @Description 常规订单资源库实现
 * @Version 1.0
 */
@Repository
public class RegularOrderRepositoryImpl implements RegularOrderRepository {
    @Autowired
    private RegularOrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public boolean insertBatch(List<RegularOrder> orders) {
        List<RegularOrderPO> pos=new ArrayList<>();
        List<OrderItemPO> poItems=new ArrayList<>();
        orders.forEach(order->{
            RegularOrderPO orderPO = OrderConvert.convertToPO(order);
            pos.add(orderPO);
            List<OrderItemPO> orderItemPOs = OrderItemConvert.convertToPO(order.getItems());
            poItems.addAll(orderItemPOs);
        });
        orderMapper.insertBatch(pos);
        orderItemMapper.insertBatch(poItems);
        return true;
    }



    @Override
    public boolean save(RegularOrder order) {
        RegularOrderPO orderPO = OrderConvert.convertToPO(order);
        List<OrderItemPO> orderItemPOs = OrderItemConvert.convertToPO(order.getItems());
        String idVal = orderPO.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(orderMapper.selectById((Serializable) idVal))) {
            orderMapper.updateById(orderPO);
        }
        saveOrderItem(orderItemPOs);
        return true;
    }

    private void saveOrderItem(List<OrderItemPO> itemPOs) {
        itemPOs.forEach(po->{
            String idVal = po.getId();
            if (!StringUtils.checkValNull(idVal) && !Objects.isNull(orderItemMapper.selectById((Serializable) idVal))) {
                orderItemMapper.updateById(po);
            }
        });
        return;
    }


    @Override
    public RegularOrder findById(String id) {
        return null;
    }


}
