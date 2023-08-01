package com.rc.cloud.app.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@DubboComponentScan(basePackages = "com.rc.cloud.app.dubbo")
@EnableDubbo(scanBasePackages = "com.rc.cloud.app.dubbo")
public class RcAppDubboBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcAppDubboBizApplication.class, args);
    }

}
