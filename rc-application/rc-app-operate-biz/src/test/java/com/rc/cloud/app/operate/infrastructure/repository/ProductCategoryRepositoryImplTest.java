package com.rc.cloud.app.operate.infrastructure.repository;

import com.rc.cloud.app.operate.ApplicationTest;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.BrandRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
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
    private ProductCategoryRepository productCategoryRepository;
    public void setUp() {

    }
    @Test
    public void test() {
        System.out.println("aa");
        productCategoryRepository.findById(new ProductCategoryId("a"));
    }


}
