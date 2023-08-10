package com.rc.cloud.app.operate.core;

import com.rc.cloud.common.core.util.IpUtils;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import org.junit.jupiter.api.BeforeEach;

/**
 * @ClassName AbstractDbUnitTest
 * @Author liandy
 * @Date 2023/8/10 08:39
 * @Description TODO
 * @Version 1.0
 */
public abstract class AbstractUnitTest extends BaseDbAndRedisUnitTest {
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId(IpUtils.getHostName());
        initFixture();
    }

    /**
     * 初始化夹具
     */
    protected abstract void initFixture();
}
