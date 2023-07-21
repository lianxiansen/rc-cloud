package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRebuildFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.ConditionUtil;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.TenantContext;
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

/**
 * @ClassName: ProductCategoryApplicationServiceTest
 * @Author: liandy
 * @Date: 2023/7/7 08:07
 * 1.创建根级产品分类
 * 2.创建子级产品分类
 * |-2.1创建子级产品分类，指定无效的父产品分类，提示创建失败
 * 3.修改产品分类
 * |-3.1修改产品分类的上级时，（更新当前分类所有子分类层级）
 * |-3.2修改产品分类的上级时，不可将自己指定为上级分类
 * 4.获取产品分类列表
 * 5.删除产品分类
 * |-5.1删除的产品分类
 * |-5.2删除的产品分类有产品关联该分类，阻止删除
 * |-5.3删除的产品分类有子分类，阻止删除
 * 6.获取产品分类
 */
@ExtendWith({SpringExtension.class})
@Import({ProductCategoryDomainService.class, ProductCategoryRepositoryImpl.class, ProductCategoryDomainService.class,
        ProductCategoryRefreshListener.class, ProductCategoryApplicationService.class, LocalIdRepositoryImpl.class, ProductRepositoryImpl.class})
@DisplayName("产品分类应用服务集成测试")
public class ProductCategoryApplicationServiceIntegratedTest extends BaseDbUnitTest {
    @MockBean
    private ProductRepository productRepositoryStub;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductCategoryDomainService productCategoryService;

    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;
    @Resource
    private IdRepository idRepository;


    /**
     * 夹具，测试上下文，包含属性及方法
     */
    private ProductCategoryCreateDTO productCategoryCreateDTO;

    private ProductCategoryUpdateDTO productCategoryUpdateDTO;

    private ProductCategory root;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();

    }

    @Test
    @DisplayName("创建产品分类，当产品名称为空")
    public void createProductCategoryWhenNameIsEmpty() {
        productCategoryCreateDTO.setName("");
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.create(productCategoryCreateDTO);
        });

    }

    @Test
    @DisplayName("创建根级产品分类")
    public void createRootProductCategoryTest() {
        productCategoryCreateDTO.setParentId("");
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        assertEquals(productCategoryCreateDTO,productCategoryBO );
    }

    @Test
    @DisplayName("创建产品分类指定父产品分类")
    public void createSubProductCategoryWhenParentValid() {
        productCategoryCreateDTO.setParentId(root.getId().id());
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        assertEquals(productCategoryCreateDTO,productCategoryBO);


    }


    @Test
    @DisplayName("创建产品分类指定父产品分类,指定无效的父产品分类")
    public void createSubProductCategoryIfParentInvalidTest() {
        productCategoryService.remove(root.getId());
        productCategoryCreateDTO.setParentId(root.getId().id());
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.create(productCategoryCreateDTO);
        });
    }


    @Test
    @DisplayName("修改产品分类属性，除了上级分类")
    public void updateProductCategoryWhenParentIdIsNull() {
        ProductCategoryRebuildFactory.ProductCategoryRebuilder rebuilder = ProductCategoryRebuildFactory.create(root);
        rebuilder.parentId(null);
        ProductCategory root = rebuilder.rebuild();
        productCategoryUpdateDTO.setId(root.getId().id());
        ProductCategoryBO bo=productCategoryApplicationService.update(productCategoryUpdateDTO);
        assertEquals(productCategoryUpdateDTO,bo);
    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类")
    public void updateProductCategoryWhenParentIdIsNotNull() {
        //测试数据准备
        ProductCategoryBO sonFutureBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        ProductCategory sonFuture = productCategoryService.findById(new ProductCategoryId(sonFutureBO.getId()));
        productCategoryCreateDTO.setParentId(sonFutureBO.getId());
        ProductCategoryBO grandsonFutureBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        ProductCategory grandsonFuture = productCategoryService.findById(new ProductCategoryId(grandsonFutureBO.getId()));
        productCategoryUpdateDTO.setId(sonFutureBO.getId());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        //测试执行
        productCategoryApplicationService.update(productCategoryUpdateDTO);
        sonFuture = productCategoryService.findById(new ProductCategoryId(sonFutureBO.getId()));
        grandsonFuture = productCategoryService.findById(new ProductCategoryId(grandsonFutureBO.getId()));

        assertEquals(productCategoryUpdateDTO,sonFutureBO, root.getLayer().increment().increment().equals(grandsonFuture.getLayer())&&
                root.getLayer().increment().equals(sonFuture.getLayer()));

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类为当前正在修改的产品分类")
    public void updateProductCategoryWhenParentIsMyself() {
        productCategoryUpdateDTO.setId(root.getId().id());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.update(productCategoryUpdateDTO);
        });
    }


    @Test
    @DisplayName("删除产品分类")
    public void removeProductCategory() {
        when(productRepositoryStub.existsByProductCategoryId(root.getId())).thenReturn(false);
        Assertions.assertTrue(productCategoryApplicationService.remove(root.getId().id()), "删除产品分类失败");
    }

    @Test
    @DisplayName("删除关联产品的产品分类")
    public void removeProductCategoryWhenAssociatedProductExists() {
        when(productRepositoryStub.existsByProductCategoryId(root.getId())).thenReturn(true);
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });

    }

    @Test
    @DisplayName("删除含有子分类的产品分类")
    public void removeProductCategoryWhenSubProductCategoryExists() {
        productCategoryCreateDTO.setParentId(root.getId().id());
        productCategoryApplicationService.create(productCategoryCreateDTO);
        when(productRepositoryStub.existsByProductCategoryId(root.getId())).thenReturn(false);

        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });
    }

    @Test
    @DisplayName("获取产品分类列表")
    public void findAll() {
        List<ProductCategoryBO> bos = productCategoryApplicationService.findAll();
        Assertions.assertTrue(bos.size() > 0);
    }

    @Test
    @DisplayName("获取产品分类")
    public void findById() {
        ProductCategoryBO bo = productCategoryApplicationService.findById(root.getId().id());
        assertEquals(root,bo);
    }

    public void assertEquals(ProductCategory expected,ProductCategoryBO actual, boolean ...condition) {
        Assertions.assertTrue(actual.getId().equals(expected.getId().id()) &&
                        actual.getName().equals(expected.getChName().value()) &&
                        actual.isEnabled() == expected.getEnabled().value() &&
                        actual.getEnglishName().equals(expected.getEnName().value()) &&
                        actual.getIcon().equals(expected.getIcon().getPictureUrl()) &&
                        actual.getProductCategoryPageImage().equals(expected.getPage().getCategoryImage()) &&
                        actual.getProductListPageImage().equals(expected.getPage().getListImage()) &&
                        actual.getSort() == expected.getSort().getValue() &&
                        StringUtils.isNotEmpty(actual.getParentId()) ? actual.getParentId().equals(expected.getParentId().id()) : true&&
                        ConditionUtil.booleanValue(condition),
                "对象不相等");
    }

    public void assertEquals(ProductCategoryCreateDTO expected,ProductCategoryBO actual, boolean ...condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                        expected.getName().equals(actual.getName()) &&
                        expected.getEnabled().booleanValue() == actual.isEnabled() &&
                        expected.getEnglishName().equals(actual.getEnglishName()) &&
                        expected.getIcon().equals(actual.getIcon()) &&
                        expected.getProductCategoryPageImage().equals(actual.getProductCategoryPageImage()) &&
                        expected.getProductListPageImage().equals(actual.getProductListPageImage()) &&
                        expected.getSort().intValue() == actual.getSort() &&
                        StringUtils.isNotEmpty(actual.getParentId()) ? actual.getParentId().equals(expected.getParentId()) : true&&
                        ConditionUtil.booleanValue(condition),
                "对象不相等");
    }
    public void assertEquals(ProductCategoryUpdateDTO expected,ProductCategoryBO actual, boolean ...condition) {

        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                        expected.getName().equals(actual.getName()) &&
                        expected.getEnabled().booleanValue() == actual.isEnabled() &&
                        expected.getEnglishName().equals(actual.getEnglishName()) &&
                        expected.getIcon().equals(actual.getIcon()) &&
                        expected.getProductCategoryPageImage().equals(actual.getProductCategoryPageImage()) &&
                        expected.getProductListPageImage().equals(actual.getProductListPageImage()) &&
                        expected.getSort().intValue() == actual.getSort() &&
                        StringUtils.isNotEmpty(actual.getParentId()) ? actual.getParentId().equals(expected.getParentId()) : true&&
                        ConditionUtil.booleanValue(condition),
                "对象不相等");
    }

    private void initStub() {

    }

    private void initFixture() {
        String imgUrl = "http://127.0.0.1:9000/test/2023/07/20/56a3d87acd3b4105950be3647abc5383.jpg";
        TenantContext.setTenantId("test");
        productCategoryCreateDTO = new ProductCategoryCreateDTO();
        productCategoryCreateDTO.setProductCategoryPageImage(imgUrl).setEnglishName(RandomUtils.randomString()).setName(RandomUtils.randomString()).setIcon(imgUrl).setSort(9).setEnabled(true).setProductListPageImage(imgUrl);
        productCategoryUpdateDTO = new ProductCategoryUpdateDTO();
        productCategoryUpdateDTO.setProductCategoryPageImage(imgUrl).setEnglishName(RandomUtils.randomString()).setName(RandomUtils.randomString()).setIcon(imgUrl).setSort(9).setEnabled(true).setProductListPageImage(imgUrl);
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        root = productCategoryService.findById(new ProductCategoryId(productCategoryBO.getId()));
    }

}
