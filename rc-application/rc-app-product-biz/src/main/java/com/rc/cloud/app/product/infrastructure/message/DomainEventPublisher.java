package com.rc.cloud.app.product.infrastructure.message;

import org.springframework.stereotype.Component;

/**
 * DomainEventPublisher, this is for demo purpose, the Event is sent to EventBus
 *
 * Normally DomainEvent should be sent to Messaging Middleware
 *
 * @author Frank Zhang
 * @date 2019-01-04 11:05 AM
 */
@Component
public class DomainEventPublisher {

    public void publish(Object domainEvent) {
        //eventBus.fire(domainEvent);
    }
}
