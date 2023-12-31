package com.rc.cloud.ops.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控服务
 *
 * @author haoxin
 * @date 2021-05-24
 **/
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApplication.class, args);
    }
}
