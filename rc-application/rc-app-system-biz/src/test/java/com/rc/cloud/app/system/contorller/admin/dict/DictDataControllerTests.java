/**
 * @author oliveoil
 * date 2023-06-13 09:46
 */
package com.rc.cloud.app.system.contorller.admin.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dict.DictDataController;
import com.rc.cloud.app.system.service.dict.DictDataService;
import com.rc.cloud.app.system.vo.dict.data.DictDataCreateReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataUpdateReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link DictDataController} 类
 */
@RcTest
public class DictDataControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private DictDataService dictDataService;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId("1");
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"sys:dict:query"})
    public void getDictDataById_success() throws Exception {
        mvc.perform(get("/admin/dict-data/1236"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.label").value("男"));
    }

    @Test
    @WithMockUser("admin")
    public void listDictDataAllSimple_success() throws Exception {
        mvc.perform(get("/admin/dict-data/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].label").value("默认"));
    }

    @Test
    @WithMockUser("admin")
    public void getDictDataPage_success() throws Exception {
        mvc.perform(get("/admin/dict-data/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(206))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].label").value("管理员"));
    }

    @Test
    @WithMockUser("admin")
    public void createDictData_success() throws Exception {
        DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
        dictDataCreateReqVO.setSort(1);
        dictDataCreateReqVO.setLabel("柔川");
        dictDataCreateReqVO.setValue("rc");
        dictDataCreateReqVO.setDictType("user_type");
        dictDataCreateReqVO.setStatus(0);
        dictDataCreateReqVO.setCssClass("success");
        dictDataCreateReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dictDataCreateReqVO);
        mvc.perform(post("/admin/dict-data/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }


    @Test
    @WithMockUser("admin")
    public void updateDictData_success() throws Exception {
        DictDataUpdateReqVO dictDataUpdateReqVO = new DictDataUpdateReqVO();
        dictDataUpdateReqVO.setId("1");
        dictDataUpdateReqVO.setSort(1);
        dictDataUpdateReqVO.setLabel("柔川");
        dictDataUpdateReqVO.setValue("rc");
        dictDataUpdateReqVO.setDictType("user_type");
        dictDataUpdateReqVO.setStatus(0);
        dictDataUpdateReqVO.setCssClass("success");
        dictDataUpdateReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dictDataUpdateReqVO);
        mvc.perform(put("/admin/dict-data/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    // 根据ID删除
    @Test
    @WithMockUser("admin")
    public void deleteDictDataById_success() throws Exception {
        DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
        dictDataCreateReqVO.setSort(1);
        dictDataCreateReqVO.setLabel("柔川");
        dictDataCreateReqVO.setValue("rc");
        dictDataCreateReqVO.setDictType("user_type");
        dictDataCreateReqVO.setStatus(0);
        dictDataCreateReqVO.setCssClass("success");
        dictDataCreateReqVO.setRemark("备注");
        String dictDataId = dictDataService.createDictData(dictDataCreateReqVO);
        mvc.perform(delete("/admin/dict-data/" + dictDataId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }
}
