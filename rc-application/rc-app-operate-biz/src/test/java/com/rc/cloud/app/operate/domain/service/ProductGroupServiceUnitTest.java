package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.*;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductGroupRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.core.ut.SpringMockitoUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Objects;

import static org.mockito.Mockito.when;

@Import({ProductGroupService.class, LocalIdRepositoryImpl.class, ProductGroupRepositoryImpl.class})
@DisplayName("产品组合服务单元测试")
public class ProductGroupServiceUnitTest extends SpringMockitoUnitTest {


    @Autowired
    private ProductGroupService productGroupService;
    @Resource
    private IdRepository idRepository;
    @MockBean
    private ProductRepository productRepositoryStub;
    @MockBean
    private ProductGroupRepository productGroupRepositoryStub;
    private ProductGroupCreateDTO productGroupCreateDTO;

    private Product productMock;
    private ProductGroupId productGroupId;
    private ProductGroup productGroup;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建产品组合领域对象，当产品不存在时操作失败")
    public void createProductGroupEntityThenProductIsNotExists() {
        when(productRepositoryStub.findById(new ProductId(productGroupCreateDTO.getProductId()))).thenReturn(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
            productGroupService.create(productGroup);
        });
    }


    @Test
    @DisplayName("创建产品组合领域对象")
    public void create() {
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        productGroupService.create(productGroup);
        Assertions.assertTrue(Objects.nonNull(productGroup.getId()) && productGroupCreateDTO.sameValueAs(productGroup) , "创建组合失败");
    }


    @Test
    @DisplayName("创建组合产品领域对象，当产品组合不存在时操作失败")
    public void createItemWhenProductGroupIsNotExists() {
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroupId, productMock.getId());
            productGroupService.createItem(productGroup, item);
        });
    }

    @Test
    @DisplayName("创建组合产品领域对象，当产品不存在时操作失败")
    public void createItemWhenProductIsNotExists() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroupId, productMock.getId());
            productGroupService.createItem(productGroup, item);
        });
    }

    @Test
    @DisplayName("创建组合产品项")
    public void createItem() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupId)).thenReturn(productGroup);
        ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroupId, productMock.getId());
        productGroupService.createItem(productGroup, item);
        Assertions.assertTrue(Objects.nonNull(item.getId()) && item.getProductGroupId().equals(productGroupId) && item.getProductId().equals(productMock.getId()));
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
        productMock = new Product(new ProductId(idRepository.nextId()), new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());
        productGroupId = new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206");
        productGroup = new ProductGroup(productGroupId, productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), productMock.getId());
    }
}
