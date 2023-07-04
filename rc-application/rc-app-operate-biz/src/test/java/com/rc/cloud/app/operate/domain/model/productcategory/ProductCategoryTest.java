package com.rc.cloud.app.operate.domain.model.productcategory;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.rc.cloud.app.operate.ApplicationTest;
import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.stub.ProductCategoryRepositoryStub;
import com.rc.cloud.app.operate.domain.model.productcategory.stub.ProductRepositoryStub;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.service.ProductCategoryDomainServce;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.util.AssertUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 *  1.新增一级产品类目
 *  2.新增子级产品类目
 *  3.修改上级类目时，（更新当前分类所有子类目层级）
 *  4.修改上级类目时，不可将自己指定为上级类目
 *  5.删除产品分类
 *      5.1删除的产品分类
 *      5.2删除的产品分类有产品关联该类目，阻止删除 TODO
 *      5.3删除的产品分类有子类目，阻止删除失败 TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles(value = "test")
public class ProductCategoryTest {
    private ProductCategoryRepository productCategoryRepository;
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryDomainServce productCategoryDomainServce;


    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    @Before
    public void setUp() {
        productCategoryRepository=new ProductCategoryRepositoryStub();
        productRepository=new ProductRepositoryStub();
        ReflectUtil.setFieldValue(productCategoryDomainServce, "productCategoryRepository",productCategoryRepository);
        ReflectUtil.setFieldValue(productCategoryDomainServce, "productRepository",productRepository);
    }

    /**
     * 创建根产品类目
     */
    @Test
    public void createRootProductCategoryTest() {
        ProductCategory root = createRootProductCategory();
        Assert.equals(root.getLayer().getValue(), 1);
    }

    /**
     * 创建子产品类目
     */
    @Test
    public void createSubProductCategoryTest() {
        ProductCategory root = createRootProductCategory();
        ProductCategory sub = createSubProductCategory(root);
        AssertUtils.equals(sub.getLayer().getValue(), root.getLayer().getValue() + 1, "产品类目层级错误");
        AssertUtils.equals(root.getId(), sub.getParentId(), "产品子类目的上级类目id错误");
    }


    /**
     * 产品类目重继承其它类目
     */
    @Test
    public void reInheritProductCategory() {
        List<ProductCategory> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex = 5;
        ProductCategory sub = allList.get(subIndex);
        ProductCategory newParent = allList.get(parentIndex);
        productCategoryDomainServce.reInherit(sub,newParent);
        AssertUtils.equals(sub.getParentId(), newParent.getId(), "父级分类的id错误");
        AssertUtils.equals(sub.getLayer(), newParent.getLayer().addLayer(new Layer(1)), "分类层级错误");
        List<ProductCategory> subList=productCategoryDomainServce.findSubList(allList,sub);
        subList.forEach(item->{
            AssertUtils.equals(item.getLayer(), sub.getLayer().addLayer(new Layer(1)), "子级分类层级错误");
        });
    }


    @Test(expected = DomainException.class)
    public void reInheritProductCategoryMyself() {
        List<ProductCategory> allList = mockAllList();
        //将sub00继承root0修改为root1
        int subIndex = 1;
        int parentIndex =subIndex;
        ProductCategory sub = allList.get(subIndex);
        ProductCategory newParent = allList.get(parentIndex);
        productCategoryDomainServce.reInherit(sub,newParent);
    }
    @Test
    public void removeProductCategory(){
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory=allList.get(4);
        productCategoryDomainServce.remove(productCategory);
    }

    @Test(expected = DomainException.class)
    public void removeProductCategoryIfProductExists(){
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory=allList.get(4);
        productCategoryDomainServce.remove(productCategory);
        setUp();
    }

    @Test(expected = DomainException.class)
    public void removeProductCategoryIfSubProductCategoryExists(){
        List<ProductCategory> allList = mockAllList();
        ProductCategory productCategory=allList.get(1);
        productCategoryDomainServce.remove(productCategory);
        setUp();
    }

    private ProductCategory createRootProductCategory() {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(RandomUtils.randomString());
        ChName name = new ChName(RandomUtils.randomString());
        ;
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(RandomUtils.randomString()));
        productCategory.setIcon(new Icon(imgUrl));
        productCategory.setEnabled(new Enabled(true));
        productCategory.setPage(new Page(imgUrl, imgUrl));
        productCategory.setSort(new Sort(1));
        ProductCategory productCategoryAggregation =productCategoryDomainServce.createProductCategory(productCategory);
        return productCategoryAggregation;
    }

    private ProductCategory createSubProductCategory(ProductCategory parentProductAggregation) {
        ProductCategory productCategoryAggregation = createRootProductCategory();
        productCategoryAggregation.inherit(parentProductAggregation);
        return productCategoryAggregation;
    }

    private List<ProductCategory> mockAllList() {
        List<ProductCategory> allList = new ArrayList<>();
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
