package com.rc.cloud.app.operate.infrastructure.repository;

import cn.hutool.core.util.ReflectUtil;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.common.feign.EnableRcFeignClients;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import javax.validation.Validation;

/**
 * @ClassName: ProductCategoryRepositoryImplTest
 * @Author: liandy
 * @Date: 2023/6/27 16:55
 * @Description: TODO
 */
@Import(ProductCategoryRepositoryImpl.class)
public class ProductCategoryRepositoryImplTest extends BaseDbUnitTest {
    @Resource
    private ProductCategoryRepositoryImpl productCategoryRepositoryImpl;
    @BeforeEach
    public void setUp() {
        // 注入一个 Validator 对象
        ReflectUtil.setFieldValue(productCategoryRepositoryImpl, "remoteIdGeneratorService",
                new RemoteIdGeneratorServiceStub());
    }
    @Test
    public void test() {
        ProductCategoryDO productCategory = new ProductCategoryDO();
        productCategoryRepositoryImpl.save(productCategory);
    }


    class RemoteIdGeneratorServiceStub implements RemoteIdGeneratorService {

        @Override
        public String uidGenerator() {
            return null;
        }

        @Override
        public String getUidByRedis() {
            return null;
        }

        @Override
        public Long getUidByLocal() {
            return null;
        }
    }
}
