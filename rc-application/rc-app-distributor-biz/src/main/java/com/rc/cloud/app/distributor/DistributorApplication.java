//CHECKSTYLE:OFF
package com.rc.cloud.app.distributor;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.security.annotation.EnableRcResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WJF
 * @create 2023-06-23 10:04
 * @description TODO
 */
@SpringBootApplication
@EnableRcFeignClients
@EnableRcResourceServer
public class DistributorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributorApplication.class, args);
    }

}

