package com.rc.cloud.app.operate.infrastructure.repository;

import cn.hutool.core.util.ReflectUtil;
import com.rc.cloud.app.operate.ApplicationTest;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;

import com.rc.cloud.app.operate.stub.RemoteIdGeneratorServiceStub;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: ProductCategoryRepositoryImplTest
 * @Author: liandy
 * @Date: 2023/6/27 16:55
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationTest.class)
public class ProductCategoryRepositoryImplTest {
    @Autowired
    private ProductCategoryFactory productCategoryBuilderFactory;
    @InjectMocks
    private ProductCategoryRepositoryImpl productCategoryRepository=new ProductCategoryRepositoryImpl();
    @Mock
    private ProductCategoryMapper productCategoryMapper;
    @Mock
    private ProductMapper productMapper;
    private String imgUrl="http://www.576zx.com/storage/uploads/20220707/2b454a34bb5934ea82e5602ef14006e8.jpg";
    @Before
    public void setUp() {
        ReflectUtil.setFieldValue(productCategoryRepository, "remoteIdGeneratorService",
                new RemoteIdGeneratorServiceStub());
    }
    @Test
    public void findById() {
    }

    @Test
    public void nextId() {
        assertLengthEquals(productCategoryRepository.nextId().id(),32,"");
    }


    protected void assertLengthEquals(String aString, int expectedLength, String aMessage) {
        int length = aString.trim().length();
        if (length !=expectedLength) {
            throw new AssertionError(aMessage);
        }
    }

    @Test
    public void save() {
        ProductCategoryId id=productCategoryRepository.nextId();
        TenantId tenantId =new TenantId("test");
        Name name=new Name("极简风");
        ProductCategoryFactory.ProductCategoryBuilder builder=productCategoryBuilderFactory.builder(id,tenantId,name);
        builder.icon(new Icon(imgUrl));
        builder.enabled(new Enabled(true));
        builder.page(new Page(imgUrl, imgUrl));
        builder.sort(new Sort(99));
        ProductCategoryAggregation productCategoryAggregation= builder.build();
        productCategoryRepository.save(productCategoryAggregation);
    }


}
