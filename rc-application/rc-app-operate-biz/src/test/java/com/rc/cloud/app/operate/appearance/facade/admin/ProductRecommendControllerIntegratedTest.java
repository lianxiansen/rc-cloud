package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.application.dto.ProductRecommendCreateDTO;
import com.rc.cloud.app.operate.core.AbstractWebApplicationTest;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductRecommendMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductRecommendPO;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductRecommendControllerIntegratedTest
 * @Author: liandy
 */
@RcTest
public class ProductRecommendControllerIntegratedTest extends AbstractWebApplicationTest {

    @Resource
    private IdRepository idRepository;
    private Product productMock;
    private ProductRecommendCreateDTO productRecommendCreateDTO;
    @Resource
    private ProductRecommendMapper productRecommendMapper;
    @MockBean
    private ProductService productService;

    @Override
    protected void initFixture() {
        productMock=  mockProduct();
        when(productService.findProductById(productMock.getId())).thenReturn(productMock);
        productRecommendCreateDTO = createProductRecommendCreateDTO();
    }



    @DisplayName(value = "创建产品推荐")
    @Test
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"productId\": \"" + productMock.getId().id() + "\",\n" +
                "  \"recommendProductId\": \""+productMock.getId().id()+"\"\n" +
                "}";
        mvc.perform(post("/admin/productRecommend/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.productId").value(productRecommendCreateDTO.getProductId()))
                .andExpect(jsonPath("$.data.recommendProductId").value(productRecommendCreateDTO.getRecommendProductId()))
                .andExpect(jsonPath("$.data.recommendProductName").value(productMock.getName().getValue()))
                .andExpect(jsonPath("$.data.recommendProductImage").value(productMock.getProductListImage().getValue()));
    }

    @DisplayName(value = "解除产品推荐")
    @Test
    public void release() throws Exception {
        ProductRecommendPO po = saveProductRecommendPO();

        String requestBody = "{\n" +
                "  \"id\": \"" + po.getId() + "\"" +
                "}";

        mvc.perform(post("/admin/productRecommend/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }




    @DisplayName(value = "获取产品推荐列表")
    @Test
    public void selectList() throws Exception {
        productRecommendMapper.delete(new LambdaQueryWrapperX<ProductRecommendPO>());
        int dataSize = 10;
        List<Product> products =new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            saveProductRecommendPO();
            products.add(productMock);
        }

        String requestBody = "{\n" +
                "  \"productId\": \"" + productMock.getId().id() + "\"" +
                "}";

        when(productService.findByIdBatch(products.stream().map(p->p.getId()).distinct().collect(Collectors.toList()))).thenReturn(products);
        mvc.perform(post("/admin/productRecommend/findList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").isNotEmpty())
                .andExpect(jsonPath("$.data[0].productId").value(productRecommendCreateDTO.getProductId()))
                .andExpect(jsonPath("$.data[0].recommendProductId").value(productRecommendCreateDTO.getRecommendProductId()))
                .andExpect(jsonPath("$.data[0].recommendProductName").value(productMock.getName().getValue()))
                .andExpect(jsonPath("$.data[0].recommendProductImage").value(productMock.getProductListImage().getValue()));
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
    private ProductRecommendCreateDTO createProductRecommendCreateDTO() {
        ProductRecommendCreateDTO ProductRecommendCreateDTO = new ProductRecommendCreateDTO();
        ProductRecommendCreateDTO.setRecommendProductId(productMock.getId().id());
        ProductRecommendCreateDTO.setProductId(productMock.getId().id());
        return ProductRecommendCreateDTO;
    }
}
