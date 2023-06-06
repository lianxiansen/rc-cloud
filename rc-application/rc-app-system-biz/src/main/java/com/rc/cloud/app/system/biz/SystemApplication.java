package com.rc.cloud.app.system.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author oliveoil
 * date 2023-06-02 09:19
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.rc.cloud.app.system.biz.mapper"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
