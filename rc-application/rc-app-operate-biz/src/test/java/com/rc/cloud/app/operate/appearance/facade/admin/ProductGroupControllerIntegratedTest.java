package com.rc.cloud.app.operate.appearance.facade.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductGroupAcceptanceTest
 * @Author: liandy
 * @Date: 2023/7/17 07:57
 * 1.创建产品组合
 * 2.解除产品组合
 * 3.添加组合项
 */
@RcTest
public class ProductGroupControllerIntegratedTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ProductRepository productRepositoryStub;
    @Resource
    private IdRepository idRepository;
    private Product productMock;
    private ProductGroupId productGroupId;
    private ProductId productId;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
        TenantContext.setTenantId("test");
        productMock = new Product(new ProductId(idRepository.nextId()), new TenantId(RandomUtils.randomString()), new Name(RandomUtils.randomString()));
        when(productRepositoryStub.findById(productMock.getId())).thenReturn(productMock);
        productGroupId=new ProductGroupId("870ef1f5-39d2-4f48-8c67-ae45206");
        productId=new ProductId("5c491caf-1df2-4bad-a04b-67976a7");
    }

    @DisplayName(value = "创建产品组合")
    @Test
    public void create() throws Exception {
        ProductGroupCreateDTO productGroupCreateDTO = new ProductGroupCreateDTO();
        productGroupCreateDTO.setName(RandomUtils.randomString());
        productGroupCreateDTO.setProductId(productMock.getId().id());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(productGroupCreateDTO);
        mvc.perform(post("/operate/admin/productGroup/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
    @DisplayName(value = "解除产品组合")
    @Test
    public void release() throws Exception {
        String productGroupId="870ef1f5-39d2-4f48-8c67-ae45206";
        mvc.perform(delete("/operate/admin/productGroup/release").param("id", productGroupId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isBoolean());
    }

    @DisplayName(value = "添加组合项")
    @Test
    public void createItem() throws Exception {
        ProductGroupItemCreateDTO productGroupItemCreateDTO = new ProductGroupItemCreateDTO().setProductGroupId(productGroupId.id())
                .setProductId(productMock.getId().id());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(productGroupItemCreateDTO);
        mvc.perform(post("/operate/admin/productGroup/createItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName(value = "获取产品组合列表")
    @Test
    public void selectList() throws Exception {
        mvc.perform(get("/operate/admin/productGroup/findAll").param("productId", productId.id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}
