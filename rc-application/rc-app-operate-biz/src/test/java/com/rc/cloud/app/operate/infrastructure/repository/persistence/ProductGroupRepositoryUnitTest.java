package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductGroupRepositoryUnitTest
 * @Author: liandy
 * @Date: 2023/7/17 08:46
 * @Description: TODO
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseDbUnitTest.Application.class)
@ActiveProfiles("unit-test") // 设置使用 application-unit-test 配置文件
@Sql(scripts = {"/sql/clean.sql","/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // 每个单元测试结束后，清理 DB
@Import({  LocalIdRepositoryImpl.class, ProductGroupRepositoryImpl.class})
@DisplayName("产品组合资源库单元测试")
public class ProductGroupRepositoryUnitTest extends BaseDbUnitTest {
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Resource
    private IdRepository idRepository;
    private ProductGroupCreateDTO productGroupCreateDTO;
    private Product productMock;

    private ProductGroup productGroupMock;

    @MockBean
    private ProductRepository productRepositoryStub;

    private ProductGroupId productGroupId;
    private ProductId productId;
    @BeforeEach
    public void beforeEach() {
        initFixture();
    }


    @Test
    public void save() {
        ProductGroup productGroup = new ProductGroup(productGroupId, productGroupCreateDTO.getName(),
                new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        for(int i=0;i<10;i++){
            ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroup.getId(), productMock.getId());
            productGroup.createItem(item);
        }
        productGroupRepository.save(productGroup);
    }


    @Test
    @DisplayName("根据唯一标识查询产品组合领域对象")
    public void findById(){
        ProductGroup productGroup=  productGroupRepository.findById(new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206"));
        Assertions.assertNotNull(productGroup);
    }
    @Test
    @DisplayName("获取产品组合列表")
    public void selectList(){
        List<ProductGroup> productGroup=  productGroupRepository.findAll(productId);
        Assertions.assertNotNull(productGroup);
    }


    /**
     * 初始化夹具
     */
    private void initFixture() {
        TenantContext.setTenantId("test");
        productGroupId=new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206");
        productId=new ProductId("5c491caf-1df2-4bad-a04b-67976a7");
        productMock = new Product(productId, new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());

    }
}
