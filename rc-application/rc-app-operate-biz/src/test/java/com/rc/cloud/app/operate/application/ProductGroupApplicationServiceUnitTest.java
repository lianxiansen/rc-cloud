package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupDomainService;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
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


@Import({ProductGroupApplicationService.class, ProductGroupDomainService.class, LocalIdRepositoryImpl.class, ProductGroupRepository.class})
public class ProductGroupApplicationServiceUnitTest extends SpringMockitoUnitTest {
    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;
    @MockBean
    private ProductRepository productRepositoryStub;

    @MockBean
    private ProductGroupDomainService productGroupServiceStub;
    @MockBean
    private ProductGroupRepository productGroupRepositorySub;
    @Resource
    private IdRepository idRepository;

    private ProductGroupCreateDTO productGroupCreateDTO;

    private Product productMock;
    private ProductGroupId productGroupId;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }

    @Test
    @DisplayName("校验DTO，当产品id为空时操作失败")
    public void validateProductGroupCreateDTOWhenProductIdIsEmpty() {
        productGroupCreateDTO.setProductId("");
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        });
    }

    @Test
    @DisplayName("校验DTO，当组合名字为空时操作失败")
    public void validateProductGroupCreateDTOWhenNameIsEmpty() {
        productGroupCreateDTO.setName("");
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        });
    }





    @Test
    @DisplayName("创建产品组合业务对象")
    public void createProductGroup() {
        String name = productGroupCreateDTO.getName();
        TenantId tenantId=new TenantId(TenantContext.getTenantId());
        ProductId productId=new ProductId(productGroupCreateDTO.getProductId());
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), name, tenantId, productId);
        when(productGroupServiceStub.create(name,tenantId,productId)).thenReturn(productGroup);
        ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        Assertions.assertTrue(Objects.nonNull(productGroupBO.getId()) && productGroupCreateDTO.sameValueAs(productGroupBO), "创建组合失败");
    }

    @Test
    @DisplayName("解除组合，当产品组合id为空时，操作失败")
    public void releaseProductGroupWhenProductGroupIdIsEmpty() {
        String productGroupId = String.valueOf("");
        Assertions.assertThrows(ServiceException.class, () -> {
            boolean released = productGroupApplicationService.release(productGroupId);
        });
    }

    @Test
    @DisplayName("解除组合")
    public void releaseProductGroup() {
        ProductGroupId productGroupId=new ProductGroupId(idRepository.nextId());
        when(productGroupServiceStub.release(productGroupId)).thenReturn(true);
        boolean released = productGroupApplicationService.release(productGroupId.id());
        Assertions.assertTrue(released, "解除组合失败");
    }

    @Test
    @DisplayName("校验ProductGroupItemCreateDTO，当产品组合id为空时操作失败")
    public void validateProductGroupItemCreateDTOWhenProductGroupIdIsNull() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(null)
                .setProductId(productMock.getId().id());
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupApplicationService.createItem(productGroupItemCreateDTO);
        });
    }
    @Test
    @DisplayName("校验ProductGroupItemCreateDTO，当产品id为空时操作失败")
    public void validateProductGroupItemCreateDTOWhenProductIdIsNull() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupId.id())
                .setProductId(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupApplicationService.createItem(productGroupItemCreateDTO);
        });
    }

    @Test
    @DisplayName("添加组合产品")
    public void appendGroupItem() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupId.id())
                .setProductId(productMock.getId().id());
        ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroupId, productMock.getId());
        when(productGroupServiceStub.createItem(productGroupId,productMock.getId())).thenReturn(item);
        ProductGroupItemBO itemBO=productGroupApplicationService.createItem(productGroupItemCreateDTO);
        Assertions.assertTrue(productGroupItemCreateDTO.getProductId().equals(itemBO.getProductId()),"添加组合产品失败");
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
        productMock = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());
        productGroupId=new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206");
    }
}
