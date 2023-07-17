package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.vo.ProductGroupItemVO;
import com.rc.cloud.app.operate.appearance.vo.ProductGroupVO;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.test.core.ut.SpringMockitoUnitTest;
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

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductGroupControllerUnitTest
 * @Author: liandy
 * @Date: 2023/7/17 09:28
 * @Description: TODO
 */
@Import({ProductGroupApplicationService.class, LocalIdRepositoryImpl.class,ProductGroupController.class})
@DisplayName("产品组合控制器单元测试")
@ExtendWith(SpringExtension.class)
public class ProductGroupControllerUnitTest extends SpringMockitoUnitTest {
    @Autowired
    private ProductGroupController productGroupControler;
    @Resource
    private IdRepository idRepository;
    @MockBean
    private ProductGroupApplicationService productGroupApplicationServiceStub;
    private ProductGroupId productGroupId;
    private Product productMock;
    @MockBean
    private ProductRepository productRepositoryStub;
    @BeforeEach
    public void setup() {
        productGroupId=new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206");
        productMock = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
    }
    @Test
    public void create(){
        Product productMock = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
        ProductGroupCreateDTO productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());

        ProductGroupBO bo=new ProductGroupBO();
        bo.setProductId(productGroupCreateDTO.getProductId());
        bo.setName(productGroupCreateDTO.getName());
        when(productGroupApplicationServiceStub.create(productGroupCreateDTO)).thenReturn(bo);
        CodeResult<ProductGroupVO> codeResult= productGroupControler.create(productGroupCreateDTO);
        Assertions.assertTrue(codeResult.isSuccess());
    }

    @DisplayName(value = "解除组合")
    @Test
    public void release(){
        String id= idRepository.nextId();
        when(productGroupApplicationServiceStub.release(id)).thenReturn(true);
        CodeResult<Boolean> codeResult= productGroupControler.release(id);
        Assertions.assertTrue(codeResult.isSuccess());
    }
    @DisplayName(value = "添加组合项")
    @Test
    public void createItem(){
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupId.id())
                .setProductId(productMock.getId().id());
        ProductGroupItemBO itemBO=new ProductGroupItemBO().setProductId(productMock.getId().id());
        when(productGroupApplicationServiceStub.createItem(productGroupItemCreateDTO)).thenReturn(itemBO);
        CodeResult<ProductGroupItemVO> codeResult= productGroupControler.createItem(productGroupItemCreateDTO);
        Assertions.assertTrue(codeResult.isSuccess());
    }
}
