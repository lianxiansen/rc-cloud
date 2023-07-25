package com.rc.cloud.app.system.contorller.admin.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dict.DictDataController;
import com.rc.cloud.app.system.mapper.dict.DictDataMapper;
import com.rc.cloud.app.system.mapper.dict.DictTypeMapper;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.model.dict.SysDictTypePO;
import com.rc.cloud.app.system.service.dict.DictDataService;
import com.rc.cloud.app.system.vo.dict.data.DictDataCreateReqVO;
import com.rc.cloud.app.system.vo.dict.data.DictDataUpdateReqVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import com.rc.cloud.common.test.annotation.RcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 09:46
 * 关联 {@link DictDataController} 类
 */
@RcTest
public class DictDataControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId("1");
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 创建岗位相关测试
     */
    @Nested
    class CreateDictDataTests {

        // happy path: 创建成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_success() throws Exception {
            SysDictTypePO dictType = createDictType();
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("柔川");
            dictDataCreateReqVO.setValue("rc");
            dictDataCreateReqVO.setDictType(dictType.getType());
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
            SysDictDataPO dbDictDataPO = dictDataMapper.selectByDictTypeAndValue(dictType.getType(), "rc");
            assertNotNull(dbDictDataPO);
            assertEquals(dictDataCreateReqVO.getLabel(), dbDictDataPO.getLabel());
            assertEquals(dictDataCreateReqVO.getValue(), dbDictDataPO.getValue());
            assertEquals(dictDataCreateReqVO.getDictType(), dbDictDataPO.getDictType());
            assertEquals(dictDataCreateReqVO.getStatus(), dbDictDataPO.getStatus());
            assertEquals(dictDataCreateReqVO.getCssClass(), dbDictDataPO.getCssClass());
            assertEquals(dictDataCreateReqVO.getRemark(), dbDictDataPO.getRemark());
            assertEquals(dictDataCreateReqVO.getSort(), dbDictDataPO.getSort());
        }

        // sad path1: 字典类型不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_dictTypeNotExist() throws Exception {
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("柔川");
            dictDataCreateReqVO.setValue("rc");
            dictDataCreateReqVO.setDictType("not_exist");
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
                    .andExpect(jsonPath("$.code").value(1002006001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前字典类型不存在"));
        }

        // sad path2: 字典类型为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_dictTypeEmpty() throws Exception {
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("柔川");
            dictDataCreateReqVO.setValue("rc");
            dictDataCreateReqVO.setDictType("");
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
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:字典类型不能为空"));
        }

        // sad path3: 字典数据label为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_labelEmpty() throws Exception {
            SysDictTypePO dictType = createDictType();
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("");
            dictDataCreateReqVO.setValue("rc");
            dictDataCreateReqVO.setDictType(dictType.getType());
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
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:字典标签不能为空"));
        }

        // sad path4: 字典数据value为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_valueEmpty() throws Exception {
            SysDictTypePO dictType = createDictType();
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("柔川");
            dictDataCreateReqVO.setValue("");
            dictDataCreateReqVO.setDictType(dictType.getType());
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
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:字典键值不能为空"));
        }

        // sad path5: 字典数据value重复
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_valueDuplicate() throws Exception {
            SysDictTypePO dictType = createDictType();
            SysDictDataPO dictData = createDictData(dictType.getType());
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel("柔川");
            dictDataCreateReqVO.setValue(dictData.getValue());
            dictDataCreateReqVO.setDictType(dictType.getType());
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
                    .andExpect(jsonPath("$.code").value(1002007003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该值的字典数据"));
        }

        // sad path6: 字典数据label重复
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictData_labelDuplicate() throws Exception {
            SysDictTypePO dictType = createDictType();
            SysDictDataPO dictData = createDictData(dictType.getType());
            DictDataCreateReqVO dictDataCreateReqVO = new DictDataCreateReqVO();
            dictDataCreateReqVO.setSort(1);
            dictDataCreateReqVO.setLabel(dictData.getLabel());
            dictDataCreateReqVO.setValue("rc");
            dictDataCreateReqVO.setDictType(dictType.getType());
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
                    .andExpect(jsonPath("$.code").value(1002007004))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该标签的字典数据"));
        }
    }


    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 根据ID获取岗位相关测试
     */
    @Nested
    class GetDictDataByIdTests {
        @Test
        @WithMockUser(value = "admin", authorities = {"sys:dict:query"})
        public void getDictDataById_success() throws Exception {
            SysDictTypePO dictType = createDictType();
            SysDictDataPO dictData = createDictData(dictType.getType());
            mvc.perform(get("/admin/dict-data/" + dictData.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.label").value(dictData.getLabel()))
                    .andExpect(jsonPath("$.data.value").value(dictData.getValue()))
                    .andExpect(jsonPath("$.data.dictType").value(dictData.getDictType()))
                    .andExpect(jsonPath("$.data.sort").value(dictData.getSort()))
                    .andExpect(jsonPath("$.data.cssClass").value(dictData.getCssClass()))
                    .andExpect(jsonPath("$.data.status").value(dictData.getStatus()))
                    .andExpect(jsonPath("$.data.remark").value(dictData.getRemark()));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 获取岗位简单列表相关测试
     */
    @Nested
    class GetDictDataSimpleListTests {
        @Test
        @WithMockUser("admin")
        public void listDictDataAllSimple_success() throws Exception {
            SysDictTypePO dictType = createDictType();
            SysDictDataPO dictData = createDictData(dictType.getType());
            mvc.perform(get("/admin/dict-data/list-all-simple"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].dictType").value(dictData.getDictType()))
                    .andExpect(jsonPath("$.data[0].value").value(dictData.getValue()))
                    .andExpect(jsonPath("$.data[0].label").value(dictData.getLabel()))
                    .andExpect(jsonPath("$.data[0].colorType").value(dictData.getColorType()))
                    .andExpect(jsonPath("$.data[0].cssClass").value(dictData.getCssClass()));
        }
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

    private SysDictDataPO createDictData(String dictType) {
        SysDictDataPO dictData = new SysDictDataPO();
        dictData.setSort(1);
        dictData.setLabel("值1");
        dictData.setValue("value1");
        dictData.setDictType(dictType);
        dictData.setStatus(CommonStatusEnum.ENABLE.getStatus());
        dictData.setCssClass("success");
        dictData.setColorType("default");
        dictData.setRemark("备注");
        dictDataMapper.insert(dictData);
        return dictData;
    }

    private SysDictTypePO createDictType() {
        SysDictTypePO dictTypePO = new SysDictTypePO();
        dictTypePO.setName("测试字段类型01");
        dictTypePO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        dictTypePO.setRemark("备注11");
        dictTypePO.setType("test_type1");
        dictTypeMapper.insert(dictTypePO);
        return dictTypePO;
    }
}
