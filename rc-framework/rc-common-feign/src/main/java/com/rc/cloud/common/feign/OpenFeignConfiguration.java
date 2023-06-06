package com.rc.cloud.common.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: OpenFeignConfig
 * @Author: liandy
 * @Date: 2022/4/21 13:43
 * @Description: OpenFeign配置类
 */
@Configuration
public class OpenFeignConfiguration {
    @Bean
    Logger.Level feignConfigLevel(){
        return Logger.Level.FULL;
    }
}
