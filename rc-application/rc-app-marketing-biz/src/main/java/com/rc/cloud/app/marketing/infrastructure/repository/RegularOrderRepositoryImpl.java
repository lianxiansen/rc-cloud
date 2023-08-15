package com.rc.cloud.app.marketing.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrderRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.RegularOrderConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.RegularOrderLineConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.RegularOrderLineMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.RegularOrderMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.RegularOrderLinePO;
import com.rc.cloud.app.marketing.infrastructure.repository.po.RegularOrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
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
    private RegularOrderLineMapper orderItemMapper;

    @Override
    public boolean saveBatch(List<RegularOrder> orders) {
        orders.forEach(order->{
            save(order);
        });
        return true;
    }



    @Override
    public boolean save(RegularOrder order) {
        RegularOrderPO orderPO = RegularOrderConvert.convertToPO(order);
        List<RegularOrderLinePO> orderItemPOs = RegularOrderLineConvert.convertToPO(order.getLines());
        String idVal = orderPO.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(orderMapper.selectById((Serializable) idVal))) {
            orderMapper.updateById(orderPO);
        }
        saveOrderItem(orderItemPOs);
        return true;
    }

    private void saveOrderItem(List<RegularOrderLinePO> itemPOs) {
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
