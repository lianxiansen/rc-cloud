package com.rc.cloud.app.product;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@DubboComponentScan(basePackages = "com.rc.cloud.app.marketing")
public class RcAppMarketingBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcAppMarketingBizApplication.class, args);
    }

}
