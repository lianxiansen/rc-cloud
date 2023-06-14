/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.permission.RoleController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateStatusReqVO;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link RoleController} 类
 */
@RcTest
public class RoleControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private CaptchaService captchaService;

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
    public void createRole_success() throws Exception {
        RoleCreateReqVO createReqVO = new RoleCreateReqVO();
        createReqVO.setName("测试角色");
        createReqVO.setSort(3);
        createReqVO.setCode("test_role");
        createReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(createReqVO);
        mvc.perform(post("/sys/role/create")
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
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
    public void updateRole_success() throws Exception {
        RoleUpdateReqVO updateReqVO = new RoleUpdateReqVO();
        updateReqVO.setId(101L);
        updateReqVO.setName("测试角色");
        updateReqVO.setSort(77);
        updateReqVO.setCode("test_role");
        updateReqVO.setRemark("备注");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/role/update")
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
    public void updateRoleStatus_success() throws Exception {
        RoleUpdateStatusReqVO updateStatusReqVO = new RoleUpdateStatusReqVO();
        updateStatusReqVO.setId(101L);
        updateStatusReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateStatusReqVO);
        mvc.perform(put("/sys/role/update-status")
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
    public void deleteRole_success() throws Exception {
        mvc.perform(delete("/sys/role/101")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void getRoleById_success() throws Exception {
        mvc.perform(get("/sys/role/101")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试账号"));
    }

    @Test
    public void getRolePage_success() throws Exception {
        mvc.perform(get("/sys/role/page")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(3))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("测试账号"));
    }

    @Test
    public void getRoleListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/role/list-all-simple")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").value("测试账号"));
    }

    private AuthLoginRespVO getToken() {
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
        String key = getCaptcha().getKey();
        login.setKey(key);
        String captchaCode = getCaptchaCode(key);
        login.setCaptcha(captchaCode);
        return authService.login(login);
    }

    private CaptchaVO getCaptcha() {
        return captchaService.generate();
    }

    private String getCaptchaCode(String key) {
        key = RedisKeys.getCaptchaKey(key);
        return (String) redisCache.get(key);
    }
}
