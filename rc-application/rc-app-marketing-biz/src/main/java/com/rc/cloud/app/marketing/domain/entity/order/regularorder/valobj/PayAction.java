package com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj;

/**
 * @ClassName PayAction
 * @Author liandy
 * @Date 2023/8/14 16:24
 * @Description
 * @Version 1.0
 */
public class PayAction extends OrderAction {
    private final OrderStatus requiredStatus = OrderStatus.PAYING;
    @Override
    public OrderStatus doAction(OrderStatus currentStatus) {
        if (requiredStatus == currentStatus) {
            return targetStatus;
        }
        throw new UnsupportedOperationException("当前订单状态下不支持此次操作");
    }

    @Override
    public void setTargetStatus() {
        this.targetStatus = OrderStatus.DELIVERING;
    }

}
