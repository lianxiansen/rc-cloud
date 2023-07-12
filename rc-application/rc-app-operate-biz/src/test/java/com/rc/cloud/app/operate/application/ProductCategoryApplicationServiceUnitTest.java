package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryService;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.RemoveShouldNotAssociatedProductSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.RemoveShouldNotHasChildSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.ChName;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.exception.ServiceException;
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
        ProductCategoryRefreshListener.class, ProductCategoryApplicationService.class})
@DisplayName("产品分类应用服务测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class ProductCategoryApplicationServiceUnitTest extends BaseMockitoUnitTest {
    @MockBean
    private ProductRepository productRepositoryStub;
    @MockBean
    private ProductCategoryRepository productCategoryRepositoryStub;
    @MockBean
    private RemoveShouldNotHasChildSpecification removeShouldNoChildSpecificationStub;
    @MockBean
    private RemoveShouldNotAssociatedProductSpecification removeShouldNotAssociatedProductSpecificationStub;
    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;
    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

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
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(productCategoryBO.getId()) &&
                productCategoryCreateDTO.getName().equals(productCategoryBO.getName()) &&
                productCategoryCreateDTO.getEnabledFlag().booleanValue() == productCategoryBO.isEnabled() &&
                productCategoryCreateDTO.getEnglishName().equals(productCategoryBO.getEnglishName()) &&
                productCategoryCreateDTO.getIcon().equals(productCategoryBO.getIcon()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(productCategoryBO.getProductCategoryPageImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(productCategoryBO.getProductListPageImage()) &&
                productCategoryCreateDTO.getSortId().intValue() == productCategoryBO.getSort() &&
                productCategoryCreateDTO.getTenantId().equals(productCategoryBO.getTenantId()) &&
                ObjectUtils.isNull(productCategoryBO.getParentId()), "创建根级产品分类失败");
    }

    @Test
    @DisplayName("创建产品分类指定父产品分类")
    public void createSubProductCategoryWhenParentValid() {
        productCategoryCreateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        ProductCategoryBO productCategoryBO = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(productCategoryBO.getId()) &&
                productCategoryCreateDTO.getName().equals(productCategoryBO.getName()) &&
                productCategoryCreateDTO.getEnabledFlag().booleanValue() == productCategoryBO.isEnabled()&&
                productCategoryCreateDTO.getEnglishName().equals(productCategoryBO.getEnglishName()) &&
                productCategoryCreateDTO.getIcon().equals(productCategoryBO.getIcon()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(productCategoryBO.getProductCategoryPageImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(productCategoryBO.getProductListPageImage()) &&
                productCategoryCreateDTO.getSortId().intValue() == productCategoryBO.getSort() &&
                productCategoryCreateDTO.getTenantId().equals(productCategoryBO.getTenantId()) &&
                root.getId().equals(new ProductCategoryId(productCategoryBO.getParentId())), "创建子产品分类失败");

    }


    @Test
    @DisplayName("创建产品分类指定父产品分类,指定无效的父产品分类")
    public void createSubProductCategoryIfParentInvalidTest() {
        ProductCategoryId parentId = productCategoryRepositoryStub.nextId();
        productCategoryCreateDTO.setParentId(parentId.id());
        when(productCategoryRepositoryStub.findById(parentId)).thenReturn(null);
        Assertions.assertThrows(ApplicationException.class, () -> {
            productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        });
    }


    @Test
    @DisplayName("修改产品分类属性，除了上级分类")
    public void updateProductCategoryExceptParent() {
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        productCategoryUpdateDTO.setId(root.getId().id());
        productCategoryApplicationService.updateProductCategory(productCategoryUpdateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(root.getId()) &&
                new Layer(Layer.ROOT).equals(root.getLayer()) &&
                productCategoryUpdateDTO.getName().equals(root.getChName().value()) &&
                productCategoryUpdateDTO.getEnabledFlag().equals(root.getEnabled().value()) &&
                productCategoryUpdateDTO.getEnglishName().equals(root.getEnName().value()) &&
                productCategoryUpdateDTO.getIcon().equals(root.getIcon().getPictureUrl()) &&
                productCategoryUpdateDTO.getProductCategoryPageImage().equals(root.getPage().getCategoryImage()) &&
                productCategoryUpdateDTO.getProductListPageImage().equals(root.getPage().getListImage()) &&
                productCategoryUpdateDTO.getSortId().equals(root.getSort().getValue()) , "修改产品分类属性失败");

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类")
    public void updateProductCategorySpecificParent() {
        ProductCategory sonFuture = new ProductCategory(productCategoryRepositoryStub.nextId(), new TenantId(TenantContext.getTenantId()), new ChName(RandomUtils.randomString()));
        ProductCategory grandsonFuture = new ProductCategory(productCategoryRepositoryStub.nextId(), new TenantId(TenantContext.getTenantId()), new ChName(RandomUtils.randomString()));
        grandsonFuture.inherit(sonFuture);
        productCategoryUpdateDTO.setId(sonFuture.getId().id());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryStub.findById(sonFuture.getId())).thenReturn(sonFuture);
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        when(productCategoryRepositoryStub.findAll()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if (method == "findAll") {
                    List<ProductCategory> allList = new ArrayList<>();
                    allList.add(root);
                    allList.add(sonFuture);
                    allList.add(grandsonFuture);
                    return allList;
                }
                return null;
            }
        });

        productCategoryApplicationService.updateProductCategory(productCategoryUpdateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(sonFuture.getId()) &&
                root.getLayer().increment().equals(sonFuture.getLayer()) &&
                root.getLayer().increment().increment().equals(grandsonFuture.getLayer()) &&
                productCategoryUpdateDTO.getName().equals(sonFuture.getChName().value()) &&
                productCategoryUpdateDTO.getEnabledFlag().equals(sonFuture.getEnabled().value()) &&
                productCategoryUpdateDTO.getEnglishName().equals(sonFuture.getEnName().value()) &&
                productCategoryUpdateDTO.getIcon().equals(sonFuture.getIcon().getPictureUrl()) &&
                productCategoryUpdateDTO.getProductCategoryPageImage().equals(sonFuture.getPage().getCategoryImage()) &&
                productCategoryUpdateDTO.getProductListPageImage().equals(sonFuture.getPage().getListImage()) &&
                productCategoryUpdateDTO.getSortId().equals(sonFuture.getSort().getValue()) &&
                root.getId().equals(sonFuture.getParentId()), "创建子产品分类失败");

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类为当前正在修改的产品分类")
    public void updateProductCategorySpecificParentMyself() {
        productCategoryUpdateDTO.setId(root.getId().id());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryApplicationService.updateProductCategory(productCategoryUpdateDTO);
        });
    }


    @Test
    @DisplayName("删除产品分类")
    public void removeProductCategory() {
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        when( removeShouldNotAssociatedProductSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(true);
        when( removeShouldNoChildSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(true);
        when(productCategoryRepositoryStub.removeById(root.getId())).thenReturn(true);
        Assertions.assertTrue(productCategoryApplicationService.remove(root.getId().id()), "删除产品分类失败");
    }

    @Test
    @DisplayName("删除关联产品的产品分类")
    public void removeProductCategoryIfProductExists() {
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        when( removeShouldNotAssociatedProductSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(false);
        when( removeShouldNoChildSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(true);
        when(productCategoryRepositoryStub.removeById(root.getId())).thenReturn(true);
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });

    }

    @Test
    @DisplayName("删除含有子分类的产品分类")
    public void removeProductCategoryIfSubProductCategoryExists() {
        when(productCategoryRepositoryStub.findById(root.getId())).thenReturn(root);
        when( removeShouldNotAssociatedProductSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(true);
        when( removeShouldNoChildSpecificationStub.isSatisfiedBy(root.getId())).thenReturn(false);
        when(productCategoryRepositoryStub.removeById(root.getId())).thenReturn(true);
        Assertions.assertThrows(ServiceException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });
    }

    private void initStub() {
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if (method == "nextId") {
                    return new ProductCategoryId(UUID.randomUUID().toString().substring(0, 31));
                }
                return null;
            }
        };
        when(productCategoryRepositoryStub.nextId()).thenAnswer(answer);
    }

    private void initFixture() {
        TenantContext.setTenantId("test");
        productCategoryCreateDTO = new ProductCategoryCreateDTO();
        productCategoryCreateDTO.setProductCategoryPageImage(imgUrl).setEnglishName(RandomUtils.randomString()).setName(RandomUtils.randomString()).setIcon(imgUrl).setTenantId(RandomUtils.randomString()).setSortId(9).setEnabledFlag(true).setProductListPageImage(imgUrl);

        productCategoryUpdateDTO = new ProductCategoryUpdateDTO();
        productCategoryUpdateDTO.setProductCategoryPageImage(imgUrl).setEnglishName(RandomUtils.randomString()).setName(RandomUtils.randomString()).setIcon(imgUrl).setSortId(9).setEnabledFlag(true).setProductListPageImage(imgUrl);
        root = new ProductCategory(productCategoryRepositoryStub.nextId(), new TenantId(TenantContext.getTenantId()), new ChName(RandomUtils.randomString()));
    }

}
