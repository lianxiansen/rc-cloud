/**
 * @author oliveoil
 * date 2023-05-02 07:57
 */
package com.rc.cloud.app.system.contorller.admin.dept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.v1.dept.DeptController;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link DeptController} 类
 */
@RcTest
public class DeptControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

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
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptList_success() throws Exception {
        mvc.perform(get("/sys/dept/list")
                        .header("tenant-id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("柔川信息"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptById_success() throws Exception {
        mvc.perform(get("/sys/dept/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("柔川信息"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptById_when_ParentExist_then_returnParentName() throws Exception {
        mvc.perform(get("/sys/dept/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("黄岩总公司"))
                .andExpect(jsonPath("$.data.parentName").value("柔川信息"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:query"})
    public void getDeptByIdNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(get("/sys/dept/9999999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:create"})
    public void createDept_success() throws Exception {
        DeptCreateReqVO deptCreateReqVO = new DeptCreateReqVO();
        deptCreateReqVO.setName("测试项目组001");
        deptCreateReqVO.setSort(3);
        deptCreateReqVO.setParentId("101");
        deptCreateReqVO.setPhone("12345678901");
        deptCreateReqVO.setEmail("123232@qq.com");
        deptCreateReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(deptCreateReqVO);
        mvc.perform(post("/sys/dept/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
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
        mvc.perform(put("/sys/dept/update")
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
        mvc.perform(delete("/sys/dept?id=112"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:delete"})
    public void deleteDept_when_idNotExist_then_throwNotFoundException() throws Exception {
        mvc.perform(delete("/sys/dept?id=999999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004002))
                .andExpect(jsonPath("$.msg").value("当前部门不存在"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:dept:delete"})
    public void deleteDept_when_childExist_then_throwNotFailedException() throws Exception {
        mvc.perform(delete("/sys/dept?id=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002004003))
                .andExpect(jsonPath("$.msg").value("存在子部门，无法删除"))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser(username = "admin")
    public void listDeptAllSimple_success() throws Exception {
        mvc.perform(get("/sys/dept/list-all-simple"))
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
}
