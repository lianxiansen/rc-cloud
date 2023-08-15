package com.rc.cloud.app.marketing.application.listener;

import com.rc.cloud.app.marketing.domain.entity.order.regularorder.event.OrderCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName SubmitOrderListener
 * @Author liandy
 * @Date 2023/8/2 10:55
 * @Description TODO
 * @Version 1.0
 */
@Component
public class OrderCreatedListener implements ApplicationListener<OrderCreatedEvent> {
    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {

    }
}
