package com.rc.cloud.app.distributor.application.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author WJF
 * @create 2023-06-29 14:07
 * @description TODO
 */

@Component
public class DistributorDeleteListener implements ApplicationListener<DistributorDeleteEvent> {
    @Override
    public void onApplicationEvent(DistributorDeleteEvent event) {
        String id = event.getSource().toString();
        System.out.println(id);
    }
}