package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendRepository;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @ClassName: ProductRecommendRepositoryUnitTest
 * @Author: liandy
 * @Date: 2023/7/17 08:46
 * @Description: TODO
 */
@Import({  LocalIdRepositoryImpl.class, ProductRecommendRepositoryImpl.class})
@DisplayName("产品推荐资源库单元测试")
public class ProductRecommendRepositoryUnitTest extends BaseDbUnitTest {
    @Autowired
    private ProductRecommendRepository productRecommendRepository;
    @Resource
    private IdRepository idRepository;
    @BeforeEach
    public void beforeEach() {
        initFixture();
    }



    @Test
    @DisplayName("根据唯一标识查询产品推荐领域对象")
    public void findById(){
        ProductRecommend productGroup=  productRecommendRepository.findById(new ProductRecommendId("870ef1f5-39d2-4f48-8c67-ae45206"));
        Assertions.assertNotNull(productGroup);
    }


    /**
     * 初始化夹具
     */
    private void initFixture() {
        TenantContext.setTenantId("110ef1f5-39d2-4f48-8c67-ae11111");


    }
}
