package com.rc.cloud.app.operate;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.security.annotation.EnableRcResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRcFeignClients
public class RcAppOperateBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcAppOperateBizApplication.class, args);
    }

}
