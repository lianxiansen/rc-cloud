//CHECKSTYLE:OFF
package com.rc.cloud.app;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.security.annotation.EnableRcResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

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

