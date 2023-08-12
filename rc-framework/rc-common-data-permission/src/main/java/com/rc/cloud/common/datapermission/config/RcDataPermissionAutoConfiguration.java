package com.rc.cloud.common.datapermission.config;

import com.rc.cloud.common.datapermission.core.rule.DataPermissionRuleFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.rc.cloud.common.datapermission.core.aop.DataPermissionAnnotationAdvisor;
import com.rc.cloud.common.datapermission.core.db.DataPermissionDatabaseInterceptor;
import com.rc.cloud.common.datapermission.core.rule.DataPermissionRule;
import com.rc.cloud.common.datapermission.core.rule.DataPermissionRuleFactoryImpl;
import com.rc.cloud.common.mybatis.core.util.MyBatisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 数据权限自动配置
 * */
@Configuration
public class RcDataPermissionAutoConfiguration {

    /**
     * 创建 DataPermissionRuleFactory
     * @param rules 数据权限规则列表
     * @return DataPermissionRuleFactory
     */
    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    /**
     * 创建 DataPermissionDatabaseInterceptor
     * @param interceptor MybatisPlusInterceptor
     * @param ruleFactory DataPermissionRuleFactory
     * @return DataPermissionDatabaseInterceptor
     */
    @Bean
    public DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
                                                                               DataPermissionRuleFactory ruleFactory) {
        // 创建 DataPermissionDatabaseInterceptor 拦截器
        DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    /**
     * 创建 DataPermissionAnnotationAdvisor
     * @return DataPermissionAnnotationAdvisor
     */
    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }
}
