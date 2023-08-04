package com.rc.cloud.app.marketing.domain.entity.regularorder.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName OrderCreatedEvent
 * @Author liandy
 * @Date 2023/8/2 10:01
 * @Description 订单创建事件
 * @Version 1.0
 */
public class OrderCreatedEvent extends ApplicationEvent {
    private String orderId;

    public OrderCreatedEvent(Object source) {
        super(source);
    }
}
