/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.controller.admin.permission.PermissionController;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleDataScopeReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleMenuReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignUserRoleReqVO;
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

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link PermissionController} 类
 */
@RcTest
public class PermissionControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Qualifier("springSecurityFilterChain")
    @BeforeEach
    public void setup() {
        TenantContextHolder.setTenantId(1L);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-menu"})
    public void listRoleResources_success() throws Exception {
        mvc.perform(get("/sys/permission/list-role-resources/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-menu"})
    public void assignRoleMenu_success() throws Exception {
        PermissionAssignRoleMenuReqVO reqVO = new PermissionAssignRoleMenuReqVO();
        reqVO.setRoleId(101L);
        Set<Long> menuIds = new HashSet<>();
        menuIds.add(1L);
        menuIds.add(2L);
        menuIds.add(5L);
        reqVO.setMenuIds(menuIds);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(reqVO);
        mvc.perform(post("/sys/permission/assign-role-menu")
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
    @WithMockUser(username = "admin", authorities = {"sys:permission:assign-role-data-scope"})
    public void assignRoleDataScope_success() throws Exception {
        PermissionAssignRoleDataScopeReqVO reqVO = new PermissionAssignRoleDataScopeReqVO();
        reqVO.setRoleId(101L);
        reqVO.setDataScope(1);
        Set<Long> dataScopeDeptIds = new HashSet<>();
        dataScopeDeptIds.add(1L);
        dataScopeDeptIds.add(2L);
        dataScopeDeptIds.add(5L);
        reqVO.setDataScopeDeptIds(dataScopeDeptIds);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(reqVO);
        mvc.perform(post("/sys/permission/assign-role-data-scope")
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
    @WithMockUser(username = "admin", authorities = {"sys:permission:assign-user-role"})
    public void listUserRoles_success() throws Exception {
        mvc.perform(get("/sys/permission/list-user-roles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"sys:permission:assign-user-role"})
    public void assignUserRole_success() throws Exception {
        PermissionAssignUserRoleReqVO reqVO = new PermissionAssignUserRoleReqVO();
        reqVO.setUserId(1L);
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(1L);
        roleIds.add(2L);
        roleIds.add(5L);
        reqVO.setRoleIds(roleIds);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(reqVO);
        mvc.perform(post("/sys/permission/assign-user-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }
}
