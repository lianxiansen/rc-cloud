package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.impl.ProductRecommendApplicationServiceImpl;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendDomainService;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendDomainServiceImpl;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendRepository;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductRecommendRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductRecommendMapper;
import com.rc.cloud.app.operate.infrastructure.util.ConditionUtil;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;


@Import({ProductRecommendApplicationServiceImpl.class, ProductRecommendDomainServiceImpl.class, LocalIdRepositoryImpl.class, ProductRecommendRepositoryImpl.class, ProductDomainService.class})
@ExtendWith({SpringExtension.class})
@DisplayName("产品推荐应用服务集成测试")
public class ProductRecommendApplicationServiceIntegratedTest extends BaseDbUnitTest {
    @Autowired
    private ProductRecommendApplicationServiceImpl productRecommendApplicationService;
    @MockBean
    private ProductRepository productRepositoryStub;
    @Autowired
    private ProductRecommendDomainService productRecommendDomainService;
    @Autowired
    private ProductRecommendRepository productRecommendRepository;
    @Resource
    private IdRepository idRepository;

    @Autowired
    private ProductRecommendMapper ProductRecommendMapper;

    private ProductRecommendCreateDTO productRecommendCreateDTO;

    private Product productMock;

    private ProductRecommend productRecommendMock;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建产品推荐")
    public void createProductRecommend() {
        ProductRecommendBO ProductRecommendBO = productRecommendApplicationService.create(productRecommendCreateDTO);
        assertEquals(productRecommendCreateDTO, ProductRecommendBO);
    }

    private void assertEquals(ProductRecommendCreateDTO expected, ProductRecommendBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getProductId().equals(actual.getProductId()) &&
                expected.getRecommendProductId().equals(actual.getRecommendProductId()) &&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }


    @Test
    @DisplayName("解除组合")
    public void releaseProductRecommend() {
        boolean released = productRecommendApplicationService.release(productRecommendMock.getId().id());
        Assertions.assertTrue(released, "解除组合失败");
    }




    @Test
    @DisplayName("获取产品推荐列表")
    public void findListByProductId() {
        ProductRecommendMapper.delete(new LambdaQueryWrapperX<>());
        int totalCount = 20;
        int itemNum = 8;
        for (int i = 0; i < totalCount; i++) {
            ProductRecommendBO groupBO = productRecommendApplicationService.create(productRecommendCreateDTO);
        }
        List<ProductRecommendBO> bos = productRecommendApplicationService.findListByProductId(productMock.getId().id());
        Assertions.assertEquals(bos.size(), totalCount);
    }


    /**
     * 初始化桩
     */
    private void initStub() {

    }

    /**
     * 初始化夹具
     */
    private void initFixture() {
        TenantContext.setTenantId("110ef1f5-39d2-4f48-8c67-ae11111");
        productMock = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productRecommendCreateDTO = new ProductRecommendCreateDTO();
        productRecommendCreateDTO.setProductId(productMock.getId().id());
        productRecommendCreateDTO.setRecommendProductId(productMock.getId().id());
        ProductRecommendBO ProductRecommendBO = productRecommendApplicationService.create(productRecommendCreateDTO);
        productRecommendMock = productRecommendDomainService.findById(new ProductRecommendId(ProductRecommendBO.getId()));
    }
}
