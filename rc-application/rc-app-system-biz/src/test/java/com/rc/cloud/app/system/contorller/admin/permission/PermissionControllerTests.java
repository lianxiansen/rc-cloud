/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.app.system.controller.admin.permission.PermissionController;
//import com.rc.cloud.app.system.service.auth.AdminAuthService;
//import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleDataScopeReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignRoleMenuReqVO;
import com.rc.cloud.app.system.vo.permission.permission.PermissionAssignUserRoleReqVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

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

//    @Resource
//    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

//    @Resource
//    private CaptchaService captchaService;

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
    public void listRoleResources_success() throws Exception {
        mvc.perform(get("/sys/permission/list-role-resources/1")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
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
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
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
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
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
    public void listUserRoles_success() throws Exception {
        mvc.perform(get("/sys/permission/list-user-roles/1")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
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
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    private AuthLoginRespVO getToken() {
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
//        String key = getCaptcha().getKey();
        String key = "1234";
        login.setKey(key);
        String captchaCode = getCaptchaCode(key);
        login.setCaptcha(captchaCode);
//        return authService.login(login);
        return null;
    }

//    private CaptchaVO getCaptcha() {
//        return captchaService.generate();
//    }

    private String getCaptchaCode(String key) {
        key = RedisKeys.getCaptchaKey(key);
        return (String) redisCache.get(key);
    }
}
