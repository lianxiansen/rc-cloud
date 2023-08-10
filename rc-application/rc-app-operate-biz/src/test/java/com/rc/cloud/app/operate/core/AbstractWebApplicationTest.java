package com.rc.cloud.app.operate.core;

import com.rc.cloud.common.core.util.IpUtils;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @ClassName AbstractWebApplicationTest
 * @Author liandy
 * @Date 2023/8/10 08:08
 * @Description 抽象web应用测试类
 * @Version 1.0
 */
public abstract class AbstractWebApplicationTest {
    protected MockMvc mvc;
    @Resource
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId(IpUtils.getHostName());
        initMvc();
        initFixture();

    }
    private void initMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }
    /**
     * 初始化夹具
     */
    protected abstract void initFixture();


}
