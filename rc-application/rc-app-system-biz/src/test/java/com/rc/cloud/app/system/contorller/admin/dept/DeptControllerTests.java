package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dept.DeptController;
import com.rc.cloud.app.system.mapper.dept.DeptMapper;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oliveoil
 * @date 2023-05-02 07:57
 * @description 关联 {@link DeptController} 类
 */
@RcTest
public class DeptControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private DeptMapper deptMapper;

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
     * @date 2023/07/24
     * @description 创建部门相关测试
     */
    @Nested
    class CreateDeptTests {
        /**
         * happy path 1：创建父级部门成功
         */
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
        public void createParentDept_success() throws Exception {
            DeptCreateReqVO deptCreateReqVO = new DeptCreateReqVO();
            deptCreateReqVO.setName("测试父级项目组001");
            deptCreateReqVO.setSort(1);
            deptCreateReqVO.setParentId("0");
            deptCreateReqVO.setPhone("12345678901");
            deptCreateReqVO.setLeaderUserId("1");
            deptCreateReqVO.setEmail("123232@qq.com");
            deptCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(deptCreateReqVO);
            mvc.perform(post("/admin/dept/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }

        /**
         * happy path 2：创建子级部门成功
         */
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
        public void createChildDept_success() throws Exception {
            SysDeptPO parentPO = createDept();
            DeptCreateReqVO childReqVO = new DeptCreateReqVO();
            childReqVO.setName("测试子级项目组001");
            childReqVO.setSort(1);
            childReqVO.setParentId(parentPO.getId());
            childReqVO.setPhone("12345678901");
            childReqVO.setEmail("123232@qq.com");
            childReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            childReqVO.setLeaderUserId("1");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(childReqVO);
            mvc.perform(post("/admin/dept/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        }

        /**
         * sad path 1：创建部门时，当部门名称为空时抛出异常
         */
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
        public void createDept_when_nameIsEmpty_then_throwBadRequestException() throws Exception {
            DeptCreateReqVO deptCreateReqVO = new DeptCreateReqVO();
            deptCreateReqVO.setName("");
            deptCreateReqVO.setSort(1);
            deptCreateReqVO.setParentId("0");
            deptCreateReqVO.setPhone("12345678901");
            deptCreateReqVO.setLeaderUserId("1");
            deptCreateReqVO.setEmail("123123@qq.com");
            deptCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(deptCreateReqVO);
            mvc.perform(post("/admin/dept/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(10030))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("请求参数不正确:部门名称不能为空"));
        }

        /**
         * sad path 2：创建子部门时，当父部门不存在时抛出异常
         */
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
        public void createDept_when_parentNotExist_then_throwBadRequestException() throws Exception {
            DeptCreateReqVO deptCreateReqVO = new DeptCreateReqVO();
            deptCreateReqVO.setName("测试子级项目组001");
            deptCreateReqVO.setSort(1);
            deptCreateReqVO.setParentId("999999");
            deptCreateReqVO.setPhone("12345678901");
            deptCreateReqVO.setLeaderUserId("1");
            deptCreateReqVO.setEmail("123123@qq.com");
            deptCreateReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(deptCreateReqVO);
            mvc.perform(post("/admin/dept/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002004001))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("父级部门不存在"));
        }

        /**
         * sad path 3：创建部门时，当同一个父级部门下存在该部门名称时抛出异常
         */
        @Test
        @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
        public void createDept_when_nameExist_then_throwBadRequestException() throws Exception {
            SysDeptPO parentPO = createDept();
            SysDeptPO child1PO = new SysDeptPO();
            String name = "测试子级项目组001";
            child1PO.setName(name);
            child1PO.setSort(1);
            child1PO.setParentId(parentPO.getId());
            child1PO.setPhone("12345678901");
            child1PO.setLeaderUserId("1");
            child1PO.setEmail("123123123@qq.com");
            child1PO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            deptMapper.insert(child1PO);
            DeptCreateReqVO child2VO = new DeptCreateReqVO();
            child2VO.setName(name);
            child2VO.setSort(1);
            child2VO.setParentId(parentPO.getId());
            child2VO.setPhone("12345678901");
            child2VO.setLeaderUserId("1");
            child2VO.setEmail("123123@qq.com");
            child2VO.setStatus(CommonStatusEnum.ENABLE.getStatus());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(child2VO);
            mvc.perform(post("/admin/dept/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1002004000))
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.msg").value("已经存在该名字的部门"));
        }

        /**
         * sad path 4：创建部门时，当父级部门处理禁用状态时抛出异常
         */
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query","sys:dept:create"})
    public void getDeptList_success() throws Exception {
        SysDeptPO sysDeptPO = createDept();
        mvc.perform(get("/admin/dept/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value(sysDeptPO.getName()))
                .andExpect(jsonPath("$.data[0].parentId").value(sysDeptPO.getParentId()))
                .andExpect(jsonPath("$.data[0].sort").value(sysDeptPO.getSort()))
                .andExpect(jsonPath("$.data[0].leaderUserId").value(sysDeptPO.getLeaderUserId()))
                .andExpect(jsonPath("$.data[0].phone").value(sysDeptPO.getPhone()))
                .andExpect(jsonPath("$.data[0].email").value(sysDeptPO.getEmail()))
                .andExpect(jsonPath("$.data[0].status").value(sysDeptPO.getStatus()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptById_success() throws Exception {
        mvc.perform(get("/admin/dept/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("柔川信息"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptById_when_ParentExist_then_returnParentName() throws Exception {
        mvc.perform(get("/admin/dept/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("黄岩总公司"))
                .andExpect(jsonPath("$.data.parentName").value("柔川信息"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptByIdNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(get("/admin/dept/9999999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"));
    }

    // TODO:: create dept sad path

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:update"})
    public void updateDept_success() throws Exception {
        DeptUpdateReqVO deptUpdateReqVO = new DeptUpdateReqVO();
        deptUpdateReqVO.setId("105");
        deptUpdateReqVO.setName("测试部门2");
        deptUpdateReqVO.setSort(77);
        deptUpdateReqVO.setParentId("0");
        deptUpdateReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(deptUpdateReqVO);
        mvc.perform(put("/admin/dept/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:delete"})
    public void deleteDept_success() throws Exception {
        mvc.perform(delete("/admin/dept?id=112"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:delete"})
    public void deleteDept_when_idNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(delete("/admin/dept?id=999999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:delete"})
    public void deleteDept_when_childExist_then_throwNotFailedException() throws Exception {
        mvc.perform(delete("/admin/dept?id=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004003))
                .andExpect(jsonPath("$.msg").value("存在子部门，无法删除"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser(username = "admin")
    public void listDeptAllSimple_success() throws Exception {
        mvc.perform(get("/admin/dept/list-all-simple"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }
//
//    @Test
//    public void deleteOrg_when_hasUser_then_throwNotFailedException() throws Exception {
//        mvc.perform(delete("/sys/org/7")
//                        .header("Authorization", "Bearer " + getAccessToken()))
//                .andDo(print())
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(10200))
//                .andExpect(jsonPath("$.message").value("该机构下有用户，不能删除"));
//    }

    private SysDeptPO createDept() throws Exception {
        SysDeptPO deptPO = new SysDeptPO();
        deptPO.setName("柔川信息");
        deptPO.setParentId("0");
        deptPO.setSort(1);
        deptPO.setLeaderUserId("1");
        deptPO.setPhone("12345678901");
        deptPO.setEmail("123123");
        deptPO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        deptMapper.insert(deptPO);
        return deptPO;
    }
}
