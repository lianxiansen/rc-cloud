package com.rc.cloud.app.operate.domain.model.productcategory;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.rc.cloud.app.operate.ApplicationTest;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.service.ProductCategoryDomainServce;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.app.operate.stub.RemoteIdGeneratorServiceStub;
import com.rc.cloud.common.core.util.AssertUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

;

/**
 * @ClassName: ProductCategoryTest
 * @Author: liandy
 * @Date: 2023/7/3 10:06
 * @Description: TODO
 */

/**
 * 1.新增一级产品类目
 * 2.新增子级产品类目
 * 3.修改上级类目时，（更新当前分类所有子类目层级）
 * 4.修改上级类目时，不可将自己指定为上级类目
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles(value = "test")
public class ProductCategoryTest {
    @InjectMocks
    private ProductCategoryRepositoryImpl productCategoryRepository = new ProductCategoryRepositoryImpl();
    @Mock
    private ProductCategoryMapper productCategoryMapper;
    @Mock
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryFactory productCategoryBuilderFactory;

    @InjectMocks
    private ProductCategoryDomainServce productCategoryDomainServce;

    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    @Before
    public void setUp() {
        ReflectUtil.setFieldValue(productCategoryRepository, "remoteIdGeneratorService", new RemoteIdGeneratorServiceStub());
        ReflectUtil.setFieldValue(productCategoryDomainServce, "productCategoryRepository", productCategoryRepository);
    }

    /**
     * 创建根产品类目
     */
    @Test
    public void createRootProductCategoryTest() {
        ProductCategoryAggregation root = createRootProductCategory();
        Assert.equals(root.getLayer().getValue(), 1);
    }

    /**
     * 创建子产品类目
     */
    @Test
    public void createSubProductCategoryTest() {
        ProductCategoryAggregation root = createRootProductCategory();
        ProductCategoryAggregation sub = createSubProductCategory(root);
        AssertUtils.equals(sub.getLayer().getValue(), root.getLayer().getValue() + 1, "产品类目层级错误");
        AssertUtils.equals(root.getId(), sub.getParentId(), "产品子类目的上级类目id错误");
    }


    /**
     * 产品类目重继承其它类目
     */
    @Test
    public void reInheritProductCategory() {
        List<ProductCategoryAggregation> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex = 5;
        ProductCategoryAggregation sub = allList.get(subIndex);
        ProductCategoryAggregation newParent = allList.get(parentIndex);
        productCategoryDomainServce.reInherit(sub,newParent);
        AssertUtils.equals(sub.getParentId(), newParent.getId(), "父级分类的id错误");
        AssertUtils.equals(sub.getLayer().addLayer(new Layer(1)), newParent.getLayer(), "分类层级错误");
        List<ProductCategoryAggregation> subList=productCategoryDomainServce.findSubList(allList,sub);
        subList.forEach(item->{
            AssertUtils.equals(item.getLayer().addLayer(new Layer(1)), sub.getLayer(), "子级分类层级错误");
        });
    }



    private ProductCategoryAggregation createRootProductCategory() {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(RandomUtils.randomString());
        ChName name = new ChName(RandomUtils.randomString());
        ;
        ProductCategoryFactory.ProductCategoryBuilder builder = productCategoryBuilderFactory.builder(id, tenantId, name);
        builder.enName(new EnName(RandomUtils.randomString()));
        builder.icon(new Icon(imgUrl));
        builder.enabled(new Enabled(true));
        builder.page(new Page(imgUrl, imgUrl));
        builder.sort(new Sort(1));
        ProductCategoryAggregation productCategoryAggregation =productCategoryDomainServce.createProductCategory(builder);
        return productCategoryAggregation;
    }

    private ProductCategoryAggregation createSubProductCategory(ProductCategoryAggregation parentProductAggregation) {
        ProductCategoryAggregation productCategoryAggregation = createRootProductCategory();
        productCategoryAggregation.inherit(parentProductAggregation);
        return productCategoryAggregation;
    }

    private List<ProductCategoryAggregation> mockAllList() {
        List<ProductCategoryAggregation> allList = new ArrayList<>();
        ProductCategoryAggregation root0 = createRootProductCategory();
        ProductCategoryAggregation sub00 = createSubProductCategory(root0);
        ProductCategoryAggregation sub01 = createSubProductCategory(root0);
        ProductCategoryAggregation sub000 = createSubProductCategory(sub00);
        ProductCategoryAggregation sub001 = createSubProductCategory(sub00);

        ProductCategoryAggregation root1 = createRootProductCategory();
        ProductCategoryAggregation sub10 = createSubProductCategory(root1);
        ProductCategoryAggregation sub11 = createSubProductCategory(root1);
        ProductCategoryAggregation sub100 = createSubProductCategory(sub10);
        ProductCategoryAggregation sub101 = createSubProductCategory(sub10);
        allList.add(root0);
        allList.add(sub00);
        allList.add(sub01);
        allList.add(sub000);
        allList.add(sub001);

        allList.add(root1);
        allList.add(sub10);
        allList.add(sub11);
        allList.add(sub11);
        allList.add(sub100);
        allList.add(sub101);
        return allList;
    }

}
