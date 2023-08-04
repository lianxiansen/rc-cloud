package com.rc.cloud.app.marketing.domain.entity.regularorder;

/**
 * @Interfaces RegularOrderRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  常规订单订单资源库
 * @Version 1.0
 */
public interface RegularOrderRepository {
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
