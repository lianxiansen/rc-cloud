package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductImageRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

;

/**
 * @ClassName: ProductCategoryDomainServceTest
 * @Author: liandy
 * @Date: 2023/7/3 10:06
 * @Description: TODO
 */

/**
 * 1.新增一级产品分类
 * 2.新增子级产品分类
 * 3.修改上级分类时，（更新当前分类所有子分类层级）
 * 4.修改上级分类时，不可将自己指定为上级分类
 * 5.删除产品分类
 * 5.1删除的产品分类
 * 5.2删除的产品分类有产品关联该分类，阻止删除 TODO
 * 5.3删除的产品分类有子分类，阻止删除失败 TODO
 */

@ExtendWith({SpringExtension.class})
@Import({ProductCategoryDomainServce.class, ProductCategoryRepositoryImpl.class, ProductCategoryDomainServce.class, ProductRepositoryImpl.class, ProductImageRepositoryImpl.class, ProductCategoryRefreshListener.class})
@DisplayName("产品分类领域服务测试")
public class ProductCategoryDomainServiceTest extends BaseMockitoUnitTest {

    @Autowired
    private ProductCategoryDomainServce productCategoryDomainServce;


    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    @MockBean
    private ProductRepository productRepositoryMock;
    @MockBean
    private ProductCategoryRepository productCategoryRepositoryMock;
    @Autowired
    private ApplicationContext applicationContext;

    private List<ProductCategory> mockAllList;


    @Test
    @DisplayName("统计spring容器bean")
    public void contextLoads() {
        System.out.println(applicationContext.getBeanDefinitionCount());
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }


    @Test
    @DisplayName("创建根产品分类")
    public void createRootProductCategoryTest() {
        ProductCategory root = createRootProductCategory();
        Assertions.assertTrue(root.getLayer().getValue() == 1 && !ObjectUtils.isNull(root.getId()), "创建根级产品分类失败");
    }


    @Test
    @DisplayName("创建子产品分类")
    public void createSubProductCategoryTest() {
        ProductCategory root = createRootProductCategory();
        ProductCategory sub = createSubProductCategory(root);
        Assertions.assertTrue((sub.getLayer().getValue() == root.getLayer().getValue() + 1) && root.getId().equals(sub.getParentId()) && !ObjectUtils.isNull(sub.getId()), "创建子产品分类失败");
    }


    @Test
    @DisplayName("产品分类重新继承其它分类")
    public void reInheritProductCategory() {
        List<ProductCategory> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex = 5;
        ProductCategory sub = allList.get(subIndex);
        ProductCategory newParent = allList.get(parentIndex);
        when(productCategoryRepositoryMock.findAll()).thenReturn(allList);
        productCategoryDomainServce.reInherit(sub, newParent);
        Assertions.assertEquals(sub.getParentId(), newParent.getId(), "父级分类的id错误");
        Assertions.assertEquals(sub.getLayer(), newParent.getLayer().addLayer(new Layer(1)), "分类层级错误");
        List<ProductCategory> subList = productCategoryDomainServce.findSubList(allList, sub);
        subList.forEach(item -> {
            Assertions.assertEquals(item.getLayer(), sub.getLayer().addLayer(new Layer(1)), "子级分类层级错误");
        });
    }


    @Test
    @DisplayName("产品分类重新继承本身")
    public void reInheritProductCategoryMyself() {
        List<ProductCategory> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex = subIndex;
        ProductCategory sub = allList.get(subIndex);
        ProductCategory newParent = allList.get(parentIndex);
        when(productCategoryRepositoryMock.findAll()).thenReturn(allList);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryDomainServce.reInherit(sub, newParent);
        });
    }

    @Test
    @DisplayName("产品分类重新继承本身")
    public void removeProductCategory() {
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory = allList.get(4);
        when(productCategoryRepositoryMock.remove(productCategory)).thenReturn(true);
        Assertions.assertTrue(productCategoryDomainServce.remove(productCategory), "删除产品分类失败");
    }

    @Test
    @DisplayName("删除关联产品的产品分类")
    public void removeProductCategoryIfProductExists() {
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory = allList.get(4);
        when(productRepositoryMock.existsByProductCategoryId(productCategory.getId())).thenReturn(true);
        when(productCategoryRepositoryMock.remove(productCategory)).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryDomainServce.remove(productCategory);
        });

    }

    @Test
    @DisplayName("删除含有子分类的产品分类")
    public void removeProductCategoryIfSubProductCategoryExists() {
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory = allList.get(1);
        when(productRepositoryMock.existsByProductCategoryId(productCategory.getId())).thenReturn(false);
        when(productCategoryRepositoryMock.existsChild(productCategory.getId())).thenReturn(true);
        when(productCategoryRepositoryMock.remove(productCategory)).thenReturn(true);
        Assertions.assertThrows(DomainException.class, () -> {
            productCategoryDomainServce.remove(productCategory);
        });

    }

    private ProductCategory createRootProductCategory() {
        ProductCategoryId id = new ProductCategoryId(UUID.randomUUID().toString().substring(0, 32));
        TenantId tenantId = new TenantId(RandomUtils.randomString());
        ChName name = new ChName(RandomUtils.randomString());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(RandomUtils.randomString()));
        productCategory.setIcon(new Icon(imgUrl));
        productCategory.setEnabled(new Enabled(true));
        productCategory.setPage(new Page(imgUrl, imgUrl));
        productCategory.setSort(new Sort(1));
        productCategoryDomainServce.initialize(productCategory);
        return productCategory;
    }

    private ProductCategory createSubProductCategory(ProductCategory parentProductAggregation) {
        ProductCategory productCategoryAggregation = createRootProductCategory();
        productCategoryAggregation.inherit(parentProductAggregation);
        return productCategoryAggregation;
    }

    private List<ProductCategory> mockAllList() {
        return mockAllList;
    }

    @BeforeAll
    public void beforeAll(){
        ProductCategory root0 = createRootProductCategory();
        ProductCategory sub00 = createSubProductCategory(root0);
        ProductCategory sub01 = createSubProductCategory(root0);
        ProductCategory sub000 = createSubProductCategory(sub00);
        ProductCategory sub001 = createSubProductCategory(sub00);

        ProductCategory root1 = createRootProductCategory();
        ProductCategory sub10 = createSubProductCategory(root1);
        ProductCategory sub11 = createSubProductCategory(root1);
        ProductCategory sub100 = createSubProductCategory(sub10);
        ProductCategory sub101 = createSubProductCategory(sub10);
        mockAllList.add(root0);
        mockAllList.add(sub00);
        mockAllList.add(sub01);
        mockAllList.add(sub000);
        mockAllList.add(sub001);

        mockAllList.add(root1);
        mockAllList.add(sub10);
        mockAllList.add(sub11);
        mockAllList.add(sub11);
        mockAllList.add(sub100);
        mockAllList.add(sub101);
    }

}
