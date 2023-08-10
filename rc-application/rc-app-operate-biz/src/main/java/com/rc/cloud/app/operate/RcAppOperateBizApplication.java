package com.rc.cloud.app.operate;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.security.annotation.EnableRcResourceServer;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRcFeignClients
@DubboComponentScan(basePackages = "com.rc.cloud.app.operate")
@EnableRcResourceServer
public class RcAppOperateBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcAppOperateBizApplication.class, args);
    }

}
