package com.rc.cloud.app.product;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@DubboComponentScan(basePackages = "com.rc.cloud.app.marketing")
@EnableRcFeignClients
public class RcAppMarketingBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcAppMarketingBizApplication.class, args);
    }

}
