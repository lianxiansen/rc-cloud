package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.application.event.ProductCategoryRefreshListener;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductCategoryRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.repository.ProductRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @ClassName: ProductApplicationServiceUnitTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建商品
 */


@ExtendWith({SpringExtension.class})
@Import({ProductDomainService.class})
@DisplayName("商品领域服务测试")
public class ProductDomainServiceUnitTest extends BaseMockitoUnitTest {

    @Autowired
    ProductDomainService productDomainService;

    @Autowired
    private IdRepository idRepository;

    private Product product;

    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建品牌")
    public void createProduct() {

       // productDomainService.createProduct()

       // Assertions.assertTrue(ObjectUtils.isNotNull(brandBO.getId()) && createBrandDTO.getName().equals(brandBO.getName()) && createBrandDTO.getEnabled().booleanValue() == brandBO.isEnable() && createBrandDTO.getSortId().intValue() == brandBO.getSort() && createBrandDTO.getType().equals(brandBO.getType()), "创建品牌失败");

    }

    private void initStub() {

    }
    private void initFixture() {
        TenantContext.setTenantId("test");
        product = new Product(new ProductId(idRepository.nextId()),new TenantId("test"),
                new Name("aa"));




    }




}
