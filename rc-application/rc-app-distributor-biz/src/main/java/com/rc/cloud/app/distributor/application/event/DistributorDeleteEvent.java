package com.rc.cloud.app.distributor.application.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author WJF
 * @create 2023-06-29 14:00
 * @description TODO
 */
public class DistributorDeleteEvent extends ApplicationEvent {

    public DistributorDeleteEvent(Object obj) {
        super(obj);
    }
}
