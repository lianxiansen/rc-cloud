package com.rc.cloud.common.web.config;

import com.rc.cloud.common.web.filter.SameUrlDataInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class YudaoWebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sameUrlDataInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public SameUrlDataInterceptor sameUrlDataInterceptor() {
        return new SameUrlDataInterceptor();
    }

}
