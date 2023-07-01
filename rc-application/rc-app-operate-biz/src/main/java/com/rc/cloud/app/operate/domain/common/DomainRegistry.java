package com.rc.cloud.app.operate.domain.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DomainRegistry implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {
        if (DomainRegistry.applicationContext == null) {
            DomainRegistry.applicationContext = anApplicationContext;
        }
    }


}
