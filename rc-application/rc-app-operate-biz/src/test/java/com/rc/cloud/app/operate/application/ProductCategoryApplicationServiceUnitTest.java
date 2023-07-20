package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRebuildFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryService;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductCategoryApplicationServiceTest
 * @Author: liandy
 * @Date: 2023/7/7 08:07
 * 1.创建一级产品分类
 * 2.创建子级产品分类
 * |-2.1创建子级产品分类，指定无效的父产品分类，提示创建失败
 * 3.修改产品分类
 * |-3.1修改产品分类的上级时，（更新当前分类所有子分类层级）
 * |-3.2修改产品分类的上级时，不可将自己指定为上级分类
 * 5.删除产品分类
 * |-5.1删除的产品分类
 * |-5.2删除的产品分类有产品关联该分类，阻止删除
 * |-5.3删除的产品分类有子分类，阻止删除
 */
@ExtendWith({SpringExtension.class})
@Import({ProductCategoryService.class, ProductCategoryRepositoryImpl.class, ProductCategoryService.class,
        ProductCategoryRefreshListener.class, ProductCategoryApplicationService.class, LocalIdRepositoryImpl.class, ProductRepositoryImpl.class})
@DisplayName("产品分类单元测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class ProductCategoryApplicationServiceUnitTest extends BaseDbUnitTest {
    @MockBean
    private ProductRepository productRepositoryStub;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductCategoryService productCategoryService;

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
    @DisplayName("创建根产品分类")
    public void createRootProductCategoryTest() {
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        ProductCategory productCategory=productCategoryService.findById(new ProductCategoryId(productCategoryBO.getId()));
        Assertions.assertTrue(ObjectUtils.isNotNull(productCategory) &&
                productCategoryCreateDTO.getName().equals(productCategoryBO.getName()) &&
                productCategoryCreateDTO.getEnabled().booleanValue() == productCategoryBO.isEnabled() &&
                productCategoryCreateDTO.getEnglishName().equals(productCategoryBO.getEnglishName()) &&
                productCategoryCreateDTO.getIcon().equals(productCategoryBO.getIcon()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(productCategoryBO.getProductCategoryPageImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(productCategoryBO.getProductListPageImage()) &&
                productCategoryCreateDTO.getSort().intValue() == productCategoryBO.getSort() &&
                ObjectUtils.isNull(productCategoryBO.getParentId()), "创建根级产品分类失败");
    }

    @Test
    @DisplayName("创建产品分类指定父产品分类")
    public void createSubProductCategoryWhenParentValid() {
        productCategoryCreateDTO.setParentId(root.getId().id());
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.create(productCategoryCreateDTO);
        ProductCategory productCategory=productCategoryService.findById(new ProductCategoryId(productCategoryBO.getId()));
        Assertions.assertTrue(ObjectUtils.isNotNull(productCategory) &&
                productCategoryCreateDTO.getName().equals(productCategoryBO.getName()) &&
                productCategoryCreateDTO.getEnabled().booleanValue() == productCategoryBO.isEnabled() &&
                productCategoryCreateDTO.getEnglishName().equals(productCategoryBO.getEnglishName()) &&
                productCategoryCreateDTO.getIcon().equals(productCategoryBO.getIcon()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(productCategoryBO.getProductCategoryPageImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(productCategoryBO.getProductListPageImage()) &&
                productCategoryCreateDTO.getSort().intValue() == productCategoryBO.getSort() &&
                root.getId().equals(new ProductCategoryId(productCategoryBO.getParentId())), "创建子产品分类失败");

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
        ProductCategoryRebuildFactory.ProductCategoryRebuilder rebuilder=ProductCategoryRebuildFactory.create(root);
        rebuilder.parentId(null);
        ProductCategory root=rebuilder.rebuild();
        productCategoryUpdateDTO.setId(root.getId().id());
        productCategoryApplicationService.update(productCategoryUpdateDTO);
        ProductCategory productCategory = productCategoryService.findById(root.getId());
        Assertions.assertTrue(ObjectUtils.isNotNull(productCategory) &&
                new Layer(Layer.ROOT).equals(productCategory.getLayer()) &&
                productCategoryUpdateDTO.getName().equals(productCategory.getChName().value()) &&
                productCategoryUpdateDTO.getEnabled().equals(productCategory.getEnabled().value()) &&
                productCategoryUpdateDTO.getEnglishName().equals(productCategory.getEnName().value()) &&
                productCategoryUpdateDTO.getIcon().equals(productCategory.getIcon().getPictureUrl()) &&
                productCategoryUpdateDTO.getProductCategoryPageImage().equals(productCategory.getPage().getCategoryImage()) &&
                productCategoryUpdateDTO.getProductListPageImage().equals(productCategory.getPage().getListImage()) &&
                productCategoryUpdateDTO.getSort().equals(productCategory.getSort().getValue()), "修改产品分类属性失败");

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类")
    public void updateProductCategorySpecificParent() {
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
        //测试验证
        Assertions.assertTrue(ObjectUtils.isNotNull(sonFuture.getId()) &&
                root.getLayer().increment().equals(sonFuture.getLayer()) &&
                root.getLayer().increment().increment().equals(grandsonFuture.getLayer()) &&
                productCategoryUpdateDTO.getName().equals(sonFuture.getChName().value()) &&
                productCategoryUpdateDTO.getEnabled().equals(sonFuture.getEnabled().value()) &&
                productCategoryUpdateDTO.getEnglishName().equals(sonFuture.getEnName().value()) &&
                productCategoryUpdateDTO.getIcon().equals(sonFuture.getIcon().getPictureUrl()) &&
                productCategoryUpdateDTO.getProductCategoryPageImage().equals(sonFuture.getPage().getCategoryImage()) &&
                productCategoryUpdateDTO.getProductListPageImage().equals(sonFuture.getPage().getListImage()) &&
                productCategoryUpdateDTO.getSort().equals(sonFuture.getSort().getValue()) &&
                root.getId().equals(sonFuture.getParentId()), "创建子产品分类失败");

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类为当前正在修改的产品分类")
    public void updateProductCategorySpecificParentMyself() {
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
    public void removeProductCategoryIfProductExists() {
        when(productRepositoryStub.existsByProductCategoryId(root.getId())).thenReturn(true);
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });

    }

    @Test
    @DisplayName("删除含有子分类的产品分类")
    public void removeProductCategoryIfSubProductCategoryExists() {
        productCategoryCreateDTO.setParentId(root.getId().id());
        productCategoryApplicationService.create(productCategoryCreateDTO);
        when(productRepositoryStub.existsByProductCategoryId(root.getId())).thenReturn(false);

        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });
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
