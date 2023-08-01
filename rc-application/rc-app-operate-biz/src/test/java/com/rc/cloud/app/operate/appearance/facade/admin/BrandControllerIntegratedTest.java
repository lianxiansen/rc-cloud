package com.rc.cloud.app.operate.appearance.facade.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rc.cloud.app.operate.appearance.admin.resp.BrandResponse;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.common.test.core.util.AssertUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RcTest
public class BrandControllerIntegratedTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Resource
    private IdRepository idRepository;
    private String brandId;
    private String name;
    private String logo;
    private String type;
    private Boolean enabled;
    private Integer sort;
    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        TenantContext.setTenantId("110ef1f5-39d2-4f48-8c67-ae11111");
        brandId = "f7570440-f052-462c-b6a8-984b799";
        name = RandomUtils.randomString();
        logo = RandomUtils.randomString();
        type = RandomUtils.randomString();
        enabled = true;
        sort = 1;
    }


    @DisplayName(value = "创建品牌")
    @Test
    public void create() throws Exception {
        String requestBody = "{\n" +
                "  \"id\" : \"" + brandId + "\",\n" +
                "  \"name\" : \"" + name + "\",\n" +
                "  \"logo\" : \"" + logo + "\",\n" +
                "  \"type\" : \"" + type + "\",\n" +
                "  \"enabled\" : " + enabled + ",\n" +
                "  \"sort\" : " + sort.toString() + "\n" +
                "}";
        MvcResult result = mvc.perform(post("/admin/brand/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String actural = result.getResponse().getContentAsString(Charset.defaultCharset());
        CodeResult<BrandResponse> brandResponse = JSON.parseObject(actural, new TypeReference<CodeResult<BrandResponse>>() {
        });
        String brandId = brandResponse.getData().getId();
        Brand brand = brandRepository.findById(new BrandId(brandId));
        Assertions.assertTrue(name.equals(brand.getName()) &&
                logo.equals(brand.getLogo()) &&
                type.equals(brand.getType()) &&
                enabled.booleanValue() && brand.isEnabled() &&
                sort.equals(brand.getSort())
        );
    }

    @DisplayName(value = "更新品牌")
    @Test
    public void update() throws Exception {
        String requestBody = "{\n" +
                "  \"id\" : \"" + brandId + "\",\n" +
                "  \"name\" : \"" + name + "\",\n" +
                "  \"logo\" : \"" + logo + "\",\n" +
                "  \"type\" : \"" + type + "\",\n" +
                "  \"enabled\" : " + enabled + ",\n" +
                "  \"sort\" : " + sort.toString() + "\n" +
                "}";
        MvcResult result = mvc.perform(put("/admin/brand/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();
        String actural = result.getResponse().getContentAsString(Charset.defaultCharset());
        CodeResult<BrandResponse> brandResponse = JSON.parseObject(actural, new TypeReference<CodeResult<BrandResponse>>() {
        });
        Brand brand = brandRepository.findById(new BrandId(brandId));
        Assertions.assertTrue(brandId.equals(brand.getId().id()) &&
                name.equals(brand.getName()) &&
                logo.equals(brand.getLogo()) &&
                type.equals(brand.getType()) &&
                enabled.booleanValue() && brand.isEnabled() &&
                sort.equals(brand.getSort())
        );
    }


    @DisplayName(value = "删除品牌")
    @Test
    public void remove() throws Exception {
        mvc.perform(delete("/admin/brand/remove").param("id", brandId))
                .andDo(print()).andReturn();
        Brand brand = brandRepository.findById(new BrandId(brandId));
        Assertions.assertTrue(Objects.isNull(brand));
    }

    @DisplayName(value = "品牌分页查询")
    @Test
    public void selectPageResult() throws Exception {
        MvcResult mvcResult=mvc.perform(get("/admin/brand/selectPageResult")
                        .param("pageNo", "1")
                        .param("pageSize", "10")
                        .param("name", "pinpai")
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();
        String actual=mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        String expected="{\"success\":true,\"code\":200,\"msg\":\"成功\",\"data\":{\"list\":[{\"name\":\"pinpai\",\"logo\":\"logo\",\"type\":\"mawb99yi9m\",\"sort\":99,\"enabled\":true,\"createTime\":\"2023-07-18 09:32:13\",\"id\":\"f7570440-f052-462c-b6a8-984b722\"}],\"total\":1}}";
        AssertUtils.assertJsonEquals(expected,actual);
    }

    @DisplayName(value = "根据唯一标识查找品牌")
    @Test
    public void findById() throws Exception {
        MvcResult mvcResult=mvc.perform(get("/admin/brand/findById")
                        .param("id", brandId)
                        .characterEncoding(Charset.defaultCharset())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();
        String actual=mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        String expected="{\"success\":true,\"code\":200,\"msg\":\"成功\",\"data\":{\"name\":\"1bkcfjmq6x\",\"logo\":\"logo\",\"type\":\"mawb99yi9m\",\"sort\":99,\"enabled\":true,\"createTime\":\"2023-07-18 09:32:13\",\"id\":\"f7570440-f052-462c-b6a8-984b799\"}}";
        AssertUtils.assertJsonEquals(expected,actual);
    }

}
