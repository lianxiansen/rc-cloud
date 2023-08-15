package com.rc.cloud.app.marketing.domain.entity.order.regularorder;

import java.util.List;

/**
 * @Interfaces RegularOrderRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  常规订单订单资源库
 * @Version 1.0
 */
public interface RegularOrderRepository {
    /**
     * 批量插入
     * @param orders
     * @return
     */
    boolean saveBatch(List<RegularOrder> orders);

    /**
     * 保存
     * @param order
     * @return
     */
    boolean save(RegularOrder order);

    /**
     * 查找订单
     * @param id
     * @return
     */
    RegularOrder findById(String id);


}
