package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.application.service.impl.ProductGroupApplicationServiceImpl;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupService;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductGroupRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductGroupMapper;
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


@Import({ProductGroupApplicationServiceImpl.class, ProductGroupService.class, LocalIdRepositoryImpl.class, ProductGroupRepositoryImpl.class})
@ExtendWith({SpringExtension.class})
@DisplayName("产品组合集成测试")
public class ProductGroupApplicationServiceIntegratedTest extends BaseDbUnitTest {
    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;
    @MockBean
    private ProductRepository productRepositoryStub;
    @Autowired
    private ProductGroupService productGroupService;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Resource
    private IdRepository idRepository;

    @Autowired
    private ProductGroupMapper productGroupMapper;

    private ProductGroupCreateDTO productGroupCreateDTO;

    private Product productMock;

    private ProductGroup productGroupMock;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建产品组合")
    public void createProductGroup() {
        ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        assertEquals(productGroupCreateDTO,productGroupBO);
    }
    private void assertEquals(ProductGroupCreateDTO expected, ProductGroupBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getName().equals(actual.getName()) &&
                expected.getProductId().equals(actual.getProductId()) &&
                Objects.nonNull(actual.getCreateTime())&&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }


    @Test
    @DisplayName("解除组合")
    public void releaseProductGroup() {
        boolean released = productGroupApplicationService.release(productGroupMock.getId().id());
        Assertions.assertTrue(released, "解除组合失败");
    }


    @Test
    @DisplayName("添加组合产品")
    public void appendGroupItem() {
        ProductGroupId productGroupId=productGroupMock.getId();
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO()
                .setProductGroupId(productGroupId.id())
                .setProductId(productMock.getId().id());
        ProductGroupItemBO itemBO=productGroupApplicationService.createItem(productGroupItemCreateDTO);
        Assertions.assertTrue(productGroupItemCreateDTO.getProductId().equals(itemBO.getProductId())&&
                productGroupItemCreateDTO.getProductGroupId().equals(itemBO.getProductGroupId()),"添加组合产品失败");
    }
    @Test
    @DisplayName("获取产品组合列表")
    public void findListByProductId(){
        productGroupMapper.delete(new LambdaQueryWrapperX<>());
        int totalCount=20;
        int itemNum=8;
        for (int i = 0; i < totalCount; i++) {
            ProductGroupBO groupBO=productGroupApplicationService.create(productGroupCreateDTO);
            ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO()
                    .setProductGroupId(groupBO.getId())
                    .setProductId(productMock.getId().id());
            for(int j = 0; j < itemNum; j++){
                productGroupApplicationService.createItem(productGroupItemCreateDTO);
            }
        }
        List<ProductGroupBO> bos= productGroupApplicationService.findListByProductId(productMock.getId().id());
        Assertions.assertEquals(bos.size(),totalCount);
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
        TenantContext.setTenantId("test");
        productMock = new Product(new ProductId(idRepository.nextId()),new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());
        ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        productGroupMock=productGroupService.findById(new ProductGroupId(productGroupBO.getId()));
    }
}
