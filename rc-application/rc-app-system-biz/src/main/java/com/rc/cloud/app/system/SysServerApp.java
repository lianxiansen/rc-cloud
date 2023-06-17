package com.rc.cloud.app.system;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author oliveoil
 * date 2023-06-02 09:19
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${rc.info.base-package}
@SpringBootApplication()
@EnableRcFeignClients
public class SysServerApp {
    public static void main(String[] args) {
        SpringApplication.run(SysServerApp.class, args);
    }
}
