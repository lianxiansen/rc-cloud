package com.rc.cloud.app.marketing.domain.entity.comfirmorder;

/**
 * @Interfaces ComfirmOrderRepository
 * @Author liandy
 * @Date 2023/8/3 14:39
 * @Description  确认订单资源库
 * @Version 1.0
 */
public interface ComfirmOrderRepository {
    /**
     * 保存
     * @param comfirmOrder
     * @return
     */
    boolean save(ComfirmOrder comfirmOrder);


    ComfirmOrder findById(String id);
}
