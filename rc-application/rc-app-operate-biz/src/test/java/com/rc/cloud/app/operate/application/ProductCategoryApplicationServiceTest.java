package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.domain.service.ProductCategoryDomainServce;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductImageRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
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
 * @Description:用户需求（用户故事）分解为验收测试
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
@Import({ProductCategoryDomainServce.class, ProductCategoryRepositoryImpl.class, ProductCategoryDomainServce.class,
        ProductRepositoryImpl.class, ProductImageRepositoryImpl.class, ProductCategoryRefreshListener.class,
        ProductCategoryApplicationService.class})
@DisplayName("产品分类应用服务测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class ProductCategoryApplicationServiceTest {
    @MockBean
    private ProductRepository productRepositoryMock;
    @MockBean
    private ProductCategoryRepository productCategoryRepositoryMock;
    @Autowired
    private ProductCategoryDomainServce productCategoryDomainServce;
    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;
    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    /**
     * 夹具，测试上下文，包含属性及方法
     */
    private ProductCategoryCreateDTO productCategoryCreateDTO;

    private ProductCategoryUpdateDTO productCategoryUpdateDTO;

    @BeforeEach
    public void beforeEach() {
        productCategoryCreateDTO = new ProductCategoryCreateDTO();
        productCategoryCreateDTO.setProductCategoryPageImage(imgUrl)
                .setEnglishName(RandomUtils.randomString())
                .setName(RandomUtils.randomString())
                .setIcon(imgUrl)
                .setTenantId(RandomUtils.randomString())
                .setSortId(9)
                .setEnabledFlag(true)
                .setProductListPageImage(imgUrl);

        productCategoryUpdateDTO = new ProductCategoryUpdateDTO();
        productCategoryUpdateDTO.setProductCategoryPageImage(imgUrl)
                .setEnglishName(RandomUtils.randomString())
                .setName(RandomUtils.randomString())
                .setIcon(imgUrl)
                .setSortId(9)
                .setEnabledFlag(true)
                .setProductListPageImage(imgUrl);

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if(method == "nextId"){
                    return new ProductCategoryId(UUID.randomUUID().toString().substring(0,31));
                }
                return null;
            }
        };
        when(productCategoryRepositoryMock.nextId()).thenAnswer(answer);
    }

    @Test
    @DisplayName("创建根产品分类")
    public void createRootProductCategoryTest() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(root.getId()) &&
                new Layer(Layer.ROOT).equals(root.getLayer()) &&
                productCategoryCreateDTO.getName().equals(root.getChName().value()) &&
                productCategoryCreateDTO.getEnabledFlag().booleanValue()==root.getEnabled().value() &&
                productCategoryCreateDTO.getEnglishName().equals(root.getEnName().value()) &&
                productCategoryCreateDTO.getIcon().equals(root.getIcon().getPictureUrl()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(root.getPage().getCategoryImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(root.getPage().getListImage()) &&
                productCategoryCreateDTO.getSortId().intValue()==root.getSort().getValue() &&
                productCategoryCreateDTO.getTenantId().equals(root.getTenantId().id()) &&
                ObjectUtils.isNull(root.getParentId()) &&
                root.getLayer().getValue() == Layer.ROOT, "创建根级产品分类失败");
    }

    @Test
    @DisplayName("创建产品分类指定父产品分类")
    @Order(1)
    public void createSubProductCategoryIfParentValidTest() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        productCategoryCreateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);
        ProductCategory sub = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        Assertions.assertTrue(ObjectUtils.isNotNull(root.getId()) &&
                root.getLayer().increment().equals(sub.getLayer()) &&
                productCategoryCreateDTO.getName().equals(sub.getChName().value()) &&
                productCategoryCreateDTO.getEnabledFlag().equals(sub.getEnabled().value()) &&
                productCategoryCreateDTO.getEnglishName().equals(sub.getEnName().value()) &&
                productCategoryCreateDTO.getIcon().equals(sub.getIcon().getPictureUrl()) &&
                productCategoryCreateDTO.getProductCategoryPageImage().equals(sub.getPage().getCategoryImage()) &&
                productCategoryCreateDTO.getProductListPageImage().equals(sub.getPage().getListImage()) &&
                productCategoryCreateDTO.getSortId().equals(sub.getSort().getValue()) &&
                productCategoryCreateDTO.getTenantId().equals(sub.getTenantId().id()) &&
                root.getId().equals(sub.getParentId()), "创建子产品分类失败");
    }


    @Test
    @DisplayName("创建产品分类指定父产品分类,指定无效的父产品分类")
    public void createSubProductCategoryIfParentInvalidTest() {
        ProductCategoryId parentId = productCategoryRepositoryMock.nextId();
        productCategoryCreateDTO.setParentId(parentId.id());
        when(productCategoryRepositoryMock.findById(parentId)).thenReturn(null);
        Assertions.assertThrows(ApplicationException.class, () -> {
            ProductCategory sub = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        });
    }


    @Test
    @DisplayName("修改产品分类属性，除了上级分类")
    public void updateProductCategoryExceptParent() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);
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
                productCategoryUpdateDTO.getSortId().equals(root.getSort().getValue()) &&
                ObjectUtils.isNull(root.getParentId()) &&
                root.getLayer().getValue() == Layer.ROOT, "修改产品分类属性失败");

    }

    @Test
    @DisplayName("修改产品分类的上级，指定其上级分类")
    public void updateProductCategorySpecificParent() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        ProductCategory sonFuture = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);

        when(productCategoryRepositoryMock.findById(sonFuture.getId())).thenReturn(sonFuture);
        productCategoryCreateDTO.setParentId(sonFuture.getId().id());
        ProductCategory grandsonFuture = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);

        productCategoryUpdateDTO.setId(sonFuture.getId().id());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);

        when(productCategoryRepositoryMock.findAll()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getMethod().getName();
                if(method == "findAll"){
                    List<ProductCategory> allList=new ArrayList<>();
                    allList.add(root);allList.add(sonFuture);allList.add(grandsonFuture);
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
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        productCategoryUpdateDTO.setId(root.getId().id());
        productCategoryUpdateDTO.setParentId(root.getId().id());
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryApplicationService.updateProductCategory(productCategoryUpdateDTO);
        });
    }


    @Test
    @DisplayName("删除产品分类")
    public void removeProductCategory() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);
        when(productCategoryRepositoryMock.remove(root)).thenReturn(true);
        Assertions.assertTrue(productCategoryApplicationService.remove(root.getId().id()), "删除产品分类失败");
    }

    @Test
    @DisplayName("删除关联产品的产品分类")
    public void removeProductCategoryIfProductExists() {
        ProductCategory root = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        when(productCategoryRepositoryMock.findById(root.getId())).thenReturn(root);
        when(productRepositoryMock.existsByProductCategoryId(root.getId())).thenReturn(true);
        when(productCategoryRepositoryMock.remove(root)).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryApplicationService.remove(root.getId().id());
        });

    }

    @Test
    @DisplayName("删除含有子分类的产品分类")
    public void removeProductCategoryIfSubProductCategoryExists() {
        ProductCategory productCategory = productCategoryApplicationService.createProductCategory(productCategoryCreateDTO);
        when(productCategoryRepositoryMock.findById(productCategory.getId())).thenReturn(productCategory);
        when(productRepositoryMock.existsByProductCategoryId(productCategory.getId())).thenReturn(false);
        when(productCategoryRepositoryMock.existsChild(productCategory.getId())).thenReturn(true);
        when(productCategoryRepositoryMock.remove(productCategory)).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryApplicationService.remove(productCategory.getId().id());
        });
    }
}
