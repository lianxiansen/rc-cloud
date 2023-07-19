package com.rc.cloud.app.operate.appearance.facade.admin;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
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

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName: ProductCategoryControllerIntegratedTest
 * @Author: liandy
 * @Date: 2023/7/5 11:03
 * @Description: TODO
 */
@RcTest
public class ProductCategoryControllerIntegratedTest {
    @Autowired
    private WebApplicationContext context;

    private
    MockMvc mvc;
    @Autowired
    private ProductCategoryApplicationService productCategoryApplicationService;
    private static final String imgUrl = "https://t7.baidu.com/it/u=3556773076,803642467&fm=3031&app=3031&size=f242,150&n=0&f=JPEG&fmt=auto?s=A51064321779538A505174D6020010B0&sec=1688490000&t=4ef579bd316ebdc454ab321a8676bbdf";

    @MockBean
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
        TenantContext.setTenantId("test");
    }

    @Test
    @DisplayName(value = "创建产品分类")
    public void create() throws Exception {
        ProductCategoryCreateDTO dto = new ProductCategoryCreateDTO();
        dto.setProductCategoryPageImage(imgUrl)
                .setEnglishName(RandomUtils.randomString())
                .setName(RandomUtils.randomString())
                .setIcon(imgUrl)
                .setSortId(9)
                .setEnabled(true)
                .setProductListPageImage(imgUrl)
                .setParentId("72f7ae9e-2ff8-45aa-b61b-59ee900");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto);
        mvc.perform(post("/operate/admin/productCategory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }


    @Test
    @DisplayName(value = "更新产品分类")
    public void update() throws Exception {
        ProductCategoryUpdateDTO dto = new ProductCategoryUpdateDTO();
        dto.setProductCategoryPageImage(imgUrl)
                .setEnglishName(RandomUtils.randomString())
                .setName(RandomUtils.randomString())
                .setIcon(imgUrl).setSort(9)
                .setEnabled(true)
                .setProductListPageImage(imgUrl)
                .setId("72f7ae9e-2ff8-45aa-b61b-59ee913")
                .setParentId("72f7ae9e-2ff8-45aa-b61b-59ee910");

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto);
        mvc.perform(put("/operate/admin/productCategory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName(value = "删除产品分类")
    @Test
    public void remove() throws Exception {
        String id="72f7ae9e-2ff8-45aa-b61b-59ee913";
        mvc.perform(delete("/operate/admin/productCategory/remove").param("id", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }


    @DisplayName(value = "产品分类列表")
    @Test
    public void findAll() throws Exception {
        mvc.perform(get("/operate/admin/productCategory/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
}
