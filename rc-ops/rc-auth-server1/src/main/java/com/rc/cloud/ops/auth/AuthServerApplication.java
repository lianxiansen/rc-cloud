package com.rc.cloud.ops.auth;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.ops.auth.remote.AuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证服务
 *
 * @author haoxin
 * @date 2021-06-15
 **/
@SpringBootApplication
@EnableRcFeignClients(clients = {AuthenticationService.class})
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class);
    }
}
