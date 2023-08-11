package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductRecommendBO;
import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.application.service.impl.ProductRecommendApplicationServiceImpl;
import com.rc.cloud.app.operate.core.AbstractUnitTest;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendService;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.LocalIdRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.ProductRecommendRepositoryImpl;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductRecommendMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductRecommendPO;
import com.rc.cloud.app.operate.infrastructure.util.ConditionUtil;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;


@Import({ProductRecommendApplicationServiceImpl.class, ProductRecommendService.class, LocalIdRepositoryImpl.class, ProductRecommendRepositoryImpl.class, ProductService.class})
@ExtendWith({SpringExtension.class})
@DisplayName("产品推荐应用服务集成测试")
public class ProductRecommendApplicationServiceIntegratedTest extends AbstractUnitTest {
    @Autowired
    private ProductRecommendApplicationServiceImpl productRecommendApplicationService;
    @Resource
    private IdRepository idRepository;

    @Autowired
    private ProductRecommendMapper ProductRecommendMapper;
    @Resource
    private ProductRecommendMapper productRecommendMapper;
    private ProductRecommendCreateDTO productRecommendCreateDTO;

    private Product productMock;
    @MockBean
    private ProductService productService;
    @Override
    public void initFixture() {
        productMock =  mockProduct();
        when(productService.findProductById(productMock.getId())).thenReturn(productMock);
        productRecommendCreateDTO= createProductRecommendCreateDTO();
    }


    @Test
    @DisplayName("创建产品推荐")
    public void createProductRecommend() {
        ProductRecommendBO ProductRecommendBO = productRecommendApplicationService.create(productRecommendCreateDTO);
        assertEquals(productRecommendCreateDTO, ProductRecommendBO);
    }

    private void assertEquals(ProductRecommendCreateDTO expected, ProductRecommendBO actual, boolean... condition) {
        Assertions.assertTrue(Objects.nonNull(actual.getId()) &&
                expected.getProductId().equals(actual.getProductId()) &&
                expected.getRecommendProductId().equals(actual.getRecommendProductId()) &&
                ConditionUtil.booleanValue(condition), "创建品牌失败");
    }


    @Test
    @DisplayName("解除组合")
    public void releaseProductRecommend() {
        ProductRecommendPO po=saveProductRecommendPO();
        boolean released = productRecommendApplicationService.release(po.getId());
        Assertions.assertTrue(released, "解除组合失败");
    }




    @Test
    @DisplayName("获取产品推荐列表")
    public void findListByProductId() {
        ProductRecommendMapper.delete(new LambdaQueryWrapperX<>());
        int totalCount = 20;
        List<Product> products =new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            saveProductRecommendPO();
            products.add(productMock);
        }
        when(productService.findByIdBatch(products.stream().map(p->p.getId()).distinct().collect(Collectors.toList()))).thenReturn(products);
        List<ProductRecommendBO> bos = productRecommendApplicationService.findList(productMock.getId().id());
        Assertions.assertEquals(bos.size(), totalCount);
    }

    private ProductRecommendCreateDTO createProductRecommendCreateDTO() {
        ProductRecommendCreateDTO   productRecommendCreateDTO = new ProductRecommendCreateDTO();
        productRecommendCreateDTO.setProductId(productMock.getId().id());
        productRecommendCreateDTO.setRecommendProductId(productMock.getId().id());
        return productRecommendCreateDTO;
    }
    private Product mockProduct() {
        Product  productMock = new Product(new ProductId(idRepository.nextId()), new Name(RandomUtils.randomString()));
        productMock.setName(new Name("齿轮大象粘钩"));
        productMock.setProductListImage(new Url("http://www.576zx.com/storage/uploads/20210809/b2c1196140eda25fb21fa9d40fcbf0f8.jpg"));
        return productMock;
    }
    private ProductRecommendPO saveProductRecommendPO() {
        ProductRecommendPO po = new ProductRecommendPO();
        po.setRecommendProductId(productMock.getId().id());
        po.setProductId(productMock.getId().id());
        po.setId(idRepository.nextId());
        productRecommendMapper.insert(po);
        return po;
    }

}
