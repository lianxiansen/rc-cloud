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
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

;

/**
 * @ClassName: ProductCategoryDomainServceTest
 * @Author: liandy
 * @Date: 2023/7/3 10:06
 * @Description: TODO
 */


@ExtendWith({SpringExtension.class})
@Import({ProductCategoryDomainServce.class, ProductCategoryRepositoryImpl.class, ProductCategoryDomainServce.class,
        ProductRepositoryImpl.class, ProductImageRepositoryImpl.class, ProductCategoryRefreshListener.class})
@DisplayName("产品分类领域服务测试")
public class ProductCategoryDomainServiceTest extends BaseMockitoUnitTest {

    @Autowired
    private ProductCategoryDomainServce productCategoryDomainServce;


    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    @MockBean
    private ProductRepository productRepositoryMock;
    @MockBean
    private ProductCategoryRepository productCategoryRepositoryMock;

    private static List<ProductCategory> mockAllList;


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
    @DisplayName("产品分类重新继承不存在的父级产品分类")
    public void reInheritProductCategoryIfParentNotExist() {
        List<ProductCategory> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex = 5;
        ProductCategory sub = allList.get(subIndex);
        ProductCategory newParent = allList.get(parentIndex);
        when(productCategoryRepositoryMock.findAll()).thenReturn(allList);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productCategoryDomainServce.reInherit(sub, null);
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


    private static ProductCategory createRootProductCategory() {
        ProductCategoryId id = new ProductCategoryId(UUID.randomUUID().toString().substring(0, 32));
        TenantId tenantId = new TenantId(RandomUtils.randomString());
        ChName name = new ChName(RandomUtils.randomString());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(RandomUtils.randomString()));
        productCategory.setIcon(new Icon(imgUrl));
        productCategory.setEnabled(new Enabled(true));
        productCategory.setPage(new Page(imgUrl, imgUrl));
        productCategory.setSort(new Sort(1));
        productCategory.root();
        return productCategory;
    }

    private static ProductCategory createSubProductCategory(ProductCategory parentCategory) {
        ProductCategory productCategory = createRootProductCategory();
        productCategory.inherit(parentCategory);
        return productCategory;
    }

    private List<ProductCategory> mockAllList() {
        return mockAllList;
    }

    @BeforeAll
    public static void before() {
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
        mockAllList = new ArrayList<>();
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
