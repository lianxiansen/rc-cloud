/**
 * @author oliveoil
 * date 2023-06-16 08:18
 */
package com.rc.cloud.ops.auth;

import com.rc.cloud.common.feign.EnableRcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 认证服务
 *
 * @author oliveoil
 * @date 2023-06-16
 **/
@SpringBootApplication
@EnableRcFeignClients
@EnableDiscoveryClient
public class AuthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
