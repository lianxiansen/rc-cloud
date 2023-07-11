package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.service.ProductGroupDomainService;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductGroupApplicationServiceTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建组合
 *  1.1创建组合时，产品唯一标识无效，阻止创建
 * 2.解除组合
 *  2.1解除组合时，组合唯一标识无效，解除失败
 * 3.添加组合产品
 *  3.1添加组合产品数量超过10个，阻止添加
 *  3.2添加组合产品重复，阻止添加
 *  3.3添加组合产品且产品不存在时，阻止添加
 * 4.检索产品组合列表
 *
 */
@ExtendWith({SpringExtension.class})
@Import({ProductGroupApplicationService.class, ProductGroupDomainService.class})
@DisplayName("组合应用服务测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class ProductGroupApplicationServiceUnitTest extends BaseMockitoUnitTest {
    @MockBean
    private ProductGroupRepository productGroupRepositoryStub;
    @MockBean
    private ProductRepository productRepositoryStub;

    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;

    private ProductGroupCreateDTO productGroupCreateDTO;

    private Product productMock;

    private ProductGroup productGroupMock;
    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }



    @Test
    @DisplayName("创建组合")
    public void createProductGroup() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        ProductGroupBO productGroupBO= productGroupApplicationService.createProductGroup(productGroupCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(productGroupBO.getId()) &&
                productGroupCreateDTO.getName().equals(productGroupBO.getName()), "创建组合失败");
    }

    @Test
    @DisplayName("创建组合时，产品唯一标识无效")
    public void createProductGroupWhenProductIdInvalidThenThrowException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productGroupApplicationService.createProductGroup(productGroupCreateDTO);
        });
    }

    @Test
    @DisplayName("解除组合")
    public void releaseProductGroup() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(productGroupMock);
        when(productGroupRepositoryStub.removeById(productGroupMock.getId())).thenReturn(true);
        boolean released= productGroupApplicationService.release(productGroupMock.getId().id());
        Assertions.assertTrue(released, "解除组合失败");
    }
    @Test
    @DisplayName("解除组合，唯一标识无效")
    public void releaseProductGroupWhenIdInvalidThenThrowException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(null);
        when(productGroupRepositoryStub.removeById(productGroupMock.getId())).thenReturn(true);
        Assertions.assertThrows(ApplicationException.class, () -> {
            productGroupApplicationService.release(productGroupMock.getId().id());
        });
    }


    @Test
    @DisplayName("添加组合产品")
    public void appendGroupItem() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(productGroupMock);
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        Product groupItem=new Product(productRepositoryStub.nextId(),new TenantId(RandomUtils.randomString()),new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(groupItem.getId())).thenReturn(groupItem);

        ProductGroupItemCreateDTO productGroupItemCreateDTO=new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                        .setProductId(groupItem.getId().id());
        productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        Assertions.assertEquals(1, productGroupMock.size());
    }
    @Test
    @DisplayName("添加组合产品数量超过上限")
    public void appendGroupItemWhenMoreThanLimitThenThrowsException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(productGroupMock);
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        Assertions.assertThrows(DomainException.class, () -> {
            for(int i=0;i<ProductGroup.MAX_ITEM_SIZE+1;i++){
                Product groupItem=new Product(productRepositoryStub.nextId(),new TenantId(RandomUtils.randomString()),new Name(RandomUtils.randomString()));
                ProductGroupItemCreateDTO productGroupItemCreateDTO=new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                        .setProductId(groupItem.getId().id());
                when(productRepositoryStub.findById(groupItem.getId())).thenReturn(groupItem);
                productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
            }
        });
    }

    @Test
    @DisplayName("添加组合产品重复")
    public void appendGroupItemWhenRepeatThrowsException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(productGroupMock);
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        Product groupItem=new Product(productRepositoryStub.nextId(),new TenantId(RandomUtils.randomString()),new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(groupItem.getId())).thenReturn(groupItem);
        when(productGroupRepositoryStub.itemExist(productGroupMock.getId(),groupItem.getId())).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            ProductGroupItemCreateDTO productGroupItemCreateDTO=new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                    .setProductId(groupItem.getId().id());
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        });
    }
    @Test
    @DisplayName("添加组合产品且组合产品的唯一标识无效")
    public void appendGroupItemWhenProductIdInvalidThrowsException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        when(productGroupRepositoryStub.findById(productGroupMock.getId())).thenReturn(productGroupMock);
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        Product groupItem=new Product(productRepositoryStub.nextId(),new TenantId(RandomUtils.randomString()),new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(groupItem.getId())).thenReturn(null);
        when(productGroupRepositoryStub.itemExist(productGroupMock.getId(),groupItem.getId())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ProductGroupItemCreateDTO productGroupItemCreateDTO=new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                    .setProductId(groupItem.getId().id());
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        });
    }

    @Test
    @DisplayName("检索产品组合列表")
    public void selectList(){
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        List<ProductGroup> productGroupList=new ArrayList<>();
        productGroupList.add(productGroupMock);
        when(productGroupRepositoryStub.selectList(productMock.getId())).thenReturn(productGroupList);
        List<ProductGroupBO> list=productGroupApplicationService.selectList(productMock.getId().id());
        Assertions.assertTrue(list.size()>0);
    }

    /**
     * 初始化桩
     */
    private void initStub() {
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if(invocation.getMock().getClass().getName().contains("ProductGroupRepository")){
                    String method = invocation.getMethod().getName();
                    if (method == "nextId") {
                        return new ProductGroupId(UUID.randomUUID().toString().substring(0, 31));
                    }
                    if (method == "nextItemId") {
                        return new ProductGroupItemId(UUID.randomUUID().toString().substring(0, 31));
                    }
                }
                if(invocation.getMock().getClass().getName().contains("ProductRepository")){
                    String method = invocation.getMethod().getName();
                    if (method == "nextId") {
                        return new ProductId(UUID.randomUUID().toString().substring(0, 31));
                    }
                }
                return null;
            }
        };
        when(productGroupRepositoryStub.nextId()).thenAnswer(answer);
        when(productGroupRepositoryStub.nextItemId()).thenAnswer(answer);
        when(productRepositoryStub.nextId()).thenAnswer(answer);
    }

    /**
     * 初始化夹具
     */
    private void initFixture(){
        TenantContext.setTenantId("test");
        productMock =new Product(productRepositoryStub.nextId(),new TenantId(RandomUtils.randomString()),new Name(RandomUtils.randomString()));
        productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());

        productGroupMock =new ProductGroup(productGroupRepositoryStub.nextId(),RandomUtils.randomString(),new TenantId(TenantContext.getTenantId()), productMock.getId());
    }
}
