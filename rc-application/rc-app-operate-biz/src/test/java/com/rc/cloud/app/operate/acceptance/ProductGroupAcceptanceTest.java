package com.rc.cloud.app.operate.acceptance;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupService;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.productgroup.specification.AppendProductGroupItemLimitSpecification;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductGroupRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupItemPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupPO;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductGroupApplicationServiceTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建产品组合
 * 1.1入参DTO校验 OK
 * 1.2创建产品组合领域对象 OK
 * 1.3转换为PO OK
 * 1.4写入数据库 OK
 *
 * 2.解除产品组合 OK
 * 2.1解除产品组合时，组合唯一标识无效，解除失败 OK
 *
 * 3.添加组合产品
 * 3.1入参DTO校验 OK
 * 3.2创建组合产品领域对象 OK
 * 3.2.1创建组合产品领域对象参数校验 OK
 * 3.3转换为PO OK
 * 3.4写入数据库 OK
 * 3.5添加组合产品数量超过10个，阻止添加 OK
 * 3.6添加组合产品且产品不存在时，阻止添加 OK
 *
 * 4.检索产品组合列表 OK
 */
@Import({ProductGroupApplicationService.class, ProductGroupService.class, LocalIdRepositoryImpl.class, ProductGroupRepositoryImpl.class})
@DisplayName("产品组合验收测试")
public class ProductGroupAcceptanceTest extends BaseDbUnitTest {

    @MockBean
    private ProductRepository productRepositoryStub;
    @Autowired
    private ProductGroupApplicationService productGroupApplicationService;
    @Autowired
    private ProductGroupService productGroupService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    private ProductGroupCreateDTO productGroupCreateDTO;

    private Product productMock;

    private ProductGroup productGroupMock;

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
    @DisplayName("创建产品组合领域对象，当产品不存在时操作失败")
    public void createProductGroupEntityThenProductIsNotExists() {
        when(productRepositoryStub.findById(new ProductId(productGroupCreateDTO.getProductId()))).thenReturn(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroup productGroup = productGroupService.create(productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        });
    }

    @Test
    @DisplayName("产品组合领域对象转化为持久层对象")
    public void convert2ProductGroupPO() {
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), productGroupCreateDTO.getName(),
                new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        ProductGroupPO po = ProductGroupConvert.convert2ProductGroupPO(productGroup);
        Assertions.assertTrue(ObjectUtils.isNotNull(po.getId()) &&
                po.getName().equals(productGroup.getName()) &&
                po.getProductId().equals(productGroup.getProductId().id()) &&
                po.getTenantId().equals(productGroup.getTenantId().id()), "创建组合失败");
    }

    @Test
    @DisplayName("产品组合领域对象持久化到资源库")
    public void saveProductGroup2Repository() {
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), productGroupCreateDTO.getName(),
                new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        ProductGroupPO po = ProductGroupConvert.convert2ProductGroupPO(productGroup);
        boolean saved = productGroupRepository.saveProductGroupPO(po);
        Assertions.assertTrue(saved, "保存产品组合实体到资源库失败");
    }


    @Test
    @DisplayName("创建产品组合领域对象")
    public void createProductGroupEntity() {
        ProductGroup productGroup = productGroupService.create(productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        Assertions.assertTrue(ObjectUtils.isNotNull(productGroup.getId()) &&
                productGroupCreateDTO.sameValueAs(productGroup) &&
                TenantContext.getTenantId().equals(productGroup.getTenantId().id()), "创建组合失败");
    }


    @Test
    @DisplayName("创建产品组合业务对象")
    public void createProductGroup() {
        ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(productGroupBO.getId()) && productGroupCreateDTO.sameValueAs(productGroupBO), "创建组合失败");
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
        boolean released = productGroupApplicationService.release(productGroupMock.getId().id());
        Assertions.assertTrue(released, "解除组合失败");
    }

    @Test
    @DisplayName("校验ProductGroupItemCreateDTO，当产品组合id为空时操作失败")
    public void validateProductGroupItemCreateDTOWhenProductGroupIdIsNull() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(null)
                .setProductId(productMock.getId().id());
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        });
    }
    @Test
    @DisplayName("校验ProductGroupItemCreateDTO，当产品id为空时操作失败")
    public void validateProductGroupItemCreateDTOWhenProductIdIsNull() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                .setProductId(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        });
    }

    @Test
    @DisplayName("创建组合产品领域对象，当产品组合不存在时操作失败")
    public void createProductGroupItemWhenProductGroupIsNotExists(){
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                .setProductId(productMock.getId().id());
        productGroupApplicationService.release(productGroupMock.getId().id());
        ProductGroup productGroup = productGroupRepository.findById(productGroupMock.getId());
        Assertions.assertNull(productGroup);
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupService.appendGroupItem(productGroupMock.getId(),productMock.getId());
        });
    }

    @Test
    @DisplayName("创建组合产品领域对象，当产品不存在时操作失败")
    public void createProductGroupItemWhenProductIsNotExists(){
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            productGroupService.appendGroupItem(productGroupMock.getId(),productMock.getId());
        });
    }
    @Test
    @DisplayName("组合产品领域对象转化为持久化对象")
    public void convert2ProductGroupItemPO(){
        ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroupMock.getId(), productMock.getId());
        ProductGroupItemPO po = ProductGroupConvert.convert2ProductGroupItemPO(item);
        Assertions.assertTrue(po.sameValueAs(item));
    }


    @Test
    @DisplayName("添加组合产品")
    public void appendGroupItem() {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                .setProductId(productMock.getId().id());
        boolean appended =productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        Assertions.assertTrue(appended,"添加组合产品失败");
    }

    @Test
    @DisplayName("添加组合产品数量超过上限")
    public void appendGroupItemWhenMoreThanLimitThenThrowsException() {
        Assertions.assertThrows(ServiceException.class, () -> {
            for (int i = 0; i < AppendProductGroupItemLimitSpecification.MAX_ITEM_SIZE + 1; i++) {
                ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                        .setProductId(productMock.getId().id());
                productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
            }
        });
    }



    @Test
    @DisplayName("添加组合产品且组合产品的唯一标识无效")
    public void appendGroupItemWhenProductIdInvalidThrowsException() {
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                    .setProductId(productMock.getId().id());
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        });
    }

    @Test
    @DisplayName("检索产品组合列表")
    public void selectList() {
        for (int i = 0; i < AppendProductGroupItemLimitSpecification.MAX_ITEM_SIZE-1; i++) {
            Product groupItem = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
            ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupMock.getId().id())
                    .setProductId(productMock.getId().id());
            productGroupApplicationService.appendGroupItem(productGroupItemCreateDTO);
        }
        List<ProductGroupBO> list = productGroupApplicationService.selectList(productMock.getId().id());
        Assertions.assertTrue(list.size() > 0);
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
        ProductGroupBO productGroupBO = productGroupApplicationService.create(productGroupCreateDTO);
        productGroupMock = productGroupService.findById(new ProductGroupId(productGroupBO.getId()));
    }
}
