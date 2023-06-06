package com.rc.cloud.resource;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.common.feign.EnableRcFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 资源服务
 *
 * @author oliveoil
 */
@SpringBootApplication()
@EnableRcFeignClients(clients = {RemoteIdGeneratorService.class})
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  资源服务模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
