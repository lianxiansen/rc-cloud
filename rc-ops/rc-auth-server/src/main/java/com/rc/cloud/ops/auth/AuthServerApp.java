/**
 * @author oliveoil
 * date 2023-06-16 08:18
 */
package com.rc.cloud.ops.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证服务
 *
 * @author oliveoil
 * @date 2023-06-16
 **/
@SpringBootApplication
public class AuthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
