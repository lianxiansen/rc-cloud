package com.rc.cloud.app.system.contorller.admin.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dict.DictTypeController;
import com.rc.cloud.app.system.mapper.dict.DictTypeMapper;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
import com.rc.cloud.app.system.model.dict.SysDictTypePO;
import com.rc.cloud.app.system.service.dict.DictTypeService;
import com.rc.cloud.app.system.vo.dict.type.DictTypeCreateReqVO;
import com.rc.cloud.app.system.vo.dict.type.DictTypeUpdateReqVO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-06-13 09:46
 * 关联 {@link DictTypeController} 类
 */
@RcTest
public class DictTypeControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private DictTypeService dictTypeService;

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
     * @description 创建字典类型相关测试
     */
    @Nested
    class CreateDictTypeTests {

        // happy path1: 创建成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictType_success() throws Exception {
            DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
            dictTypeCreateReqVO.setName("测试字段类型");
            dictTypeCreateReqVO.setStatus(0);
            dictTypeCreateReqVO.setRemark("备注");
            dictTypeCreateReqVO.setType("test_type");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dictTypeCreateReqVO);
            mvc.perform(post("/admin/dict-type/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
            SysDictTypePO dbDictTypePO = dictTypeMapper.selectByName(dictTypeCreateReqVO.getName());
            assertNotEquals(null, dbDictTypePO);
            assertEquals(dictTypeCreateReqVO.getName(), dbDictTypePO.getName());
            assertEquals(dictTypeCreateReqVO.getStatus(), dbDictTypePO.getStatus());
            assertEquals(dictTypeCreateReqVO.getRemark(), dbDictTypePO.getRemark());
            assertEquals(dictTypeCreateReqVO.getType(), dbDictTypePO.getType());
        }

        // sad path1: 字典类型名称为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictType_nameIsNull() throws Exception {
            DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
            dictTypeCreateReqVO.setName(null);
            dictTypeCreateReqVO.setStatus(0);
            dictTypeCreateReqVO.setRemark("备注");
            dictTypeCreateReqVO.setType("test_type");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dictTypeCreateReqVO);
            mvc.perform(post("/admin/dict-type/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:字典名称不能为空"));
        }

        // sad path2: 字典类型type为空
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictType_typeIsNull() throws Exception {
            DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
            dictTypeCreateReqVO.setName("测试字段类型");
            dictTypeCreateReqVO.setStatus(0);
            dictTypeCreateReqVO.setRemark("备注");
            dictTypeCreateReqVO.setType(null);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dictTypeCreateReqVO);
            mvc.perform(post("/admin/dict-type/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:字典类型不能为空"));
        }

        // sad path3: 字典类型name已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictType_nameIsExist() throws Exception {
            SysDictTypePO dictType1 = createDictType1();
            DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
            dictTypeCreateReqVO.setName(dictType1.getName());
            dictTypeCreateReqVO.setStatus(0);
            dictTypeCreateReqVO.setRemark("备注");
            dictTypeCreateReqVO.setType("test_type");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dictTypeCreateReqVO);
            mvc.perform(post("/admin/dict-type/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002006003))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该名字的字典类型"));
        }

        // sad path4: 字典类型type已存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:create"})
        public void createDictType_typeIsExist() throws Exception {
            SysDictTypePO dictType1 = createDictType1();
            DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
            dictTypeCreateReqVO.setName("测试字段类型");
            dictTypeCreateReqVO.setStatus(0);
            dictTypeCreateReqVO.setRemark("备注");
            dictTypeCreateReqVO.setType(dictType1.getType());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dictTypeCreateReqVO);
            mvc.perform(post("/admin/dict-type/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002006004))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该类型的字典类型"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 根据ID获取字典类型相关测试
     */
    @Nested
    class GetDictTypeByIDTests{

        // happy path1: 根据ID获取字典类型成功
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:query"})
        public void getDictTypeById_success() throws Exception {
            SysDictTypePO dictType1 = createDictType1();
            mvc.perform(get("/admin/dict-type/" + dictType1.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.id").value(dictType1.getId()))
                    .andExpect(jsonPath("$.data.name").value(dictType1.getName()))
                    .andExpect(jsonPath("$.data.type").value(dictType1.getType()))
                    .andExpect(jsonPath("$.data.status").value(dictType1.getStatus()))
                    .andExpect(jsonPath("$.data.remark").value(dictType1.getRemark()));
        }

        // sad path1: 根据ID获取字典类型失败，ID不存在
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dict:query"})
        public void getDictTypeById_fail_when_idNotExist() throws Exception {
            mvc.perform(get("/admin/dict-type/" + "999999"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002006001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("当前字典类型不存在"));
        }
    }

    /**
     * @author rc@hqf
     * @date 2023/07/25
     * @description 根据ID获取字典类型相关测试
     */
    @Nested
    class GetDictTypeSimpleListTests{

    }

    // list-all-simple
    @Test
    @WithMockUser(username = "admin")
    public void listDictTypeAllSimple_success() throws Exception {
        mvc.perform(get("/sys/dict-type/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("用户性别"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dict:query"})
    public void getDictTypePage_success() throws Exception {
        mvc.perform(get("/sys/dict-type/page"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("通用状态"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dict:update"})
    public void updateDictType_success() throws Exception {
        DictTypeUpdateReqVO dictTypeUpdateReqVO = new DictTypeUpdateReqVO();
        dictTypeUpdateReqVO.setId("170");
        dictTypeUpdateReqVO.setName("测试字段类型");
        dictTypeUpdateReqVO.setStatus(0);
        dictTypeUpdateReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dictTypeUpdateReqVO);
        mvc.perform(put("/sys/dict-type/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    // 根据ID删除
    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dict:delete"})
    public void deleteDictTypeById_success() throws Exception {
        DictTypeCreateReqVO dictTypeCreateReqVO = new DictTypeCreateReqVO();
        dictTypeCreateReqVO.setName("测试字段类型");
        dictTypeCreateReqVO.setStatus(0);
        dictTypeCreateReqVO.setRemark("备注");
        dictTypeCreateReqVO.setType("test_type");
        String dictTypeId = dictTypeService.createDictType(dictTypeCreateReqVO);
        mvc.perform(delete("/sys/dict-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" + dictTypeId + "]")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    private SysDictTypePO createDictType1() {
        SysDictTypePO dictTypePO = new SysDictTypePO();
        dictTypePO.setName("测试字段类型01");
        dictTypePO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        dictTypePO.setRemark("备注11");
        dictTypePO.setType("test_type1");
        dictTypeMapper.insert(dictTypePO);
        return dictTypePO;
    }

    private SysDictTypePO createDictType2() {
        SysDictTypePO dictTypePO = new SysDictTypePO();
        dictTypePO.setName("测试字段类型02");
        dictTypePO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        dictTypePO.setRemark("备注22");
        dictTypePO.setType("test_type2");
        dictTypeMapper.insert(dictTypePO);
        return dictTypePO;
    }
}
