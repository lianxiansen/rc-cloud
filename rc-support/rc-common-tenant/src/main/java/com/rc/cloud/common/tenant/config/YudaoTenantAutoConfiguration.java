package com.rc.cloud.common.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.rc.cloud.app.system.api.tenant.feign.RemoteTenantService;
import com.rc.cloud.common.core.enums.WebFilterOrderEnum;
import com.rc.cloud.common.mybatis.core.util.MyBatisUtils;
import com.rc.cloud.common.tenant.core.aop.TenantIgnoreAspect;
import com.rc.cloud.common.tenant.core.db.TenantDatabaseInterceptor;
import com.rc.cloud.common.tenant.core.service.TenantFrameworkService;
import com.rc.cloud.common.tenant.core.service.TenantFrameworkServiceImpl;
import com.rc.cloud.common.tenant.core.web.TenantContextWebFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(prefix = "rc.tenant", value = "enable", matchIfMissing = true) // 允许使用 rc.tenant.enable=false 禁用多租户
@EnableConfigurationProperties(TenantProperties.class)
public class YudaoTenantAutoConfiguration {

    @Resource
    private RemoteTenantService remoteTenantService;

    @Bean
    public TenantFrameworkService tenantFrameworkService() {
        return new TenantFrameworkServiceImpl(remoteTenantService);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // ========== WEB ==========

    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // ========== Security ==========

//    @Bean
//    public FilterRegistrationBean<TenantSecurityWebFilter> tenantSecurityWebFilter(TenantProperties tenantProperties,
//                                                                                   WebProperties webProperties,
//                                                                                   GlobalExceptionHandler globalExceptionHandler,
//                                                                                   TenantFrameworkService tenantFrameworkService) {
//        FilterRegistrationBean<TenantSecurityWebFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new TenantSecurityWebFilter(tenantProperties, webProperties,
//                globalExceptionHandler, tenantFrameworkService));
//        registrationBean.setOrder(WebFilterOrderEnum.TENANT_SECURITY_FILTER);
//        return registrationBean;
//    }

    // ========== MQ ==========

//    @Bean
//    public TenantRedisMessageInterceptor tenantRedisMessageInterceptor() {
//        return new TenantRedisMessageInterceptor();
//    }

    // ========== Job ==========

//    @Bean
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    public BeanPostProcessor jobHandlerBeanPostProcessor(TenantFrameworkService tenantFrameworkService) {
//        return new BeanPostProcessor() {
//
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                if (!(bean instanceof JobHandler)) {
//                    return bean;
//                }
//                // 有 TenantJob 注解的情况下，才会进行处理
//                if (!AnnotationUtil.hasAnnotation(bean.getClass(), TenantJob.class)) {
//                    return bean;
//                }
//
//                // 使用 TenantJobHandlerDecorator 装饰
//                return new TenantJobHandlerDecorator(tenantFrameworkService, (JobHandler) bean);
//            }
//
//        };
//    }

    // ========== Redis ==========

//    @Bean
//    @Primary // 引入租户时，tenantRedisCacheManager 为主 Bean
//    public RedisCacheManager tenantRedisCacheManager(RedisTemplate<String, Object> redisTemplate,
//                                                     RedisCacheConfiguration redisCacheConfiguration) {
//        // 创建 RedisCacheWriter 对象
//        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
//        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
//        // 创建 TenantRedisCacheManager 对象
//        return new TenantRedisCacheManager(cacheWriter, redisCacheConfiguration);
//    }

}
