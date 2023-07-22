package com.rc.cloud.app.operate.appearance.facade.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RcTest
public class BrandControllerIntegratedTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Resource
    private IdRepository idRepository;
    private String brandId=null;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
                .build();
        TenantContext.setTenantId("110ef1f5-39d2-4f48-8c67-ae11111");
        brandId="f7570440-f052-462c-b6a8-984b799";

    }

    @DisplayName(value = "创建品牌")
    @Test
    public void create() throws Exception {
        BrandCreateDTO dto = new BrandCreateDTO();
        dto = new BrandCreateDTO();
        dto.setName(RandomUtils.randomString()).setSort(1).setEnabled(new Boolean(true)).setType(RandomUtils.randomString());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto);
        mvc.perform(post("/admin/brand/create")
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

    @DisplayName(value = "更新品牌")
    @Test
    public void update() throws Exception {
        BrandUpdateDTO dto = new BrandUpdateDTO();
        dto.setName(RandomUtils.randomString())
                .setSort(1)
                .setEnabled(new Boolean(true))
                .setType(RandomUtils.randomString());
        dto.setId(brandId);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto);
        mvc.perform(put("/admin/brand/update")
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


    @DisplayName(value = "删除品牌")
    @Test
    public void remove() throws Exception {
        mvc.perform(delete("/admin/brand/remove").param("id", brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    @DisplayName(value = "品牌分页查询")
    @Test
    public void selectPageResult() throws Exception {
        mvc.perform(get("/admin/brand/selectPageResult")
                        .param("pageNo","1")
                        .param("pageSize","10")
                        .param("name","pinpai")
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @DisplayName(value = "根据唯一标识查找品牌")
    @Test
    public void findById() throws Exception {
        mvc.perform(get("/admin/brand/findById")
                        .param("id",brandId)
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

}
