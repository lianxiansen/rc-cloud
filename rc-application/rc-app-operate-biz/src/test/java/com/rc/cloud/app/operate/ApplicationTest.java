package com.rc.cloud.app.operate;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 测试启动类
 *
 * @author haoxin
 * @date 2021-04-25
 **/
@SpringBootApplication
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }

}
