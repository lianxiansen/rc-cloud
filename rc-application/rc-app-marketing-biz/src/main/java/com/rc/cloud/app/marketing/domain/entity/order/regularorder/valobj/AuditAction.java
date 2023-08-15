package com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj;

/**
 * @ClassName AuditAction
 * @Author liandy
 * @Date 2023/8/14 16:24
 * @Description 审核
 * @Version 1.0
 */
public class AuditAction extends OrderAction {
    private final OrderStatus requiredStatus = OrderStatus.AUDITING;



    @Override
    public OrderStatus doAction(OrderStatus currentStatus) {
        if (requiredStatus == currentStatus) {
            return targetStatus;
        }
        throw new UnsupportedOperationException("当前订单状态下不支持此次操作");
    }

    @Override
    public void setTargetStatus() {
        this.targetStatus = OrderStatus.PAYING;
    }

}
