package com.rc.cloud.app.marketing.domain.entity.order.regularorder.valobj;

/**
 * @ClassName Action
 * @Author liandy
 * @Date 2023/8/14 16:20
 * @Description TODO
 * @Version 1.0
 */
public abstract class OrderAction {
    protected OrderStatus targetStatus;
    public OrderAction(){

    }
    public abstract OrderStatus doAction(OrderStatus currentStatus);
    public abstract void setTargetStatus();

}
