// CHECKSTYLE:OFF
package com.rc.cloud.app.system;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.security.annotation.EnableRcResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author oliveoil
 * date 2023-06-02 09:19
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${rc.info.base-package}
@EnableDiscoveryClient
@SpringBootApplication
@EnableRcFeignClients
@EnableRcResourceServer
public class SysServerApp {
    public static void main(String[] args) {
        SpringApplication.run(SysServerApp.class, args);
    }
}
