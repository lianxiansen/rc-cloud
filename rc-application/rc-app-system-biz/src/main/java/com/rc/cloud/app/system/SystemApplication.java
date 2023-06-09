package com.rc.cloud.app.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author oliveoil
 * date 2023-06-02 09:19
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${rc.info.base-package}
@SpringBootApplication()
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
