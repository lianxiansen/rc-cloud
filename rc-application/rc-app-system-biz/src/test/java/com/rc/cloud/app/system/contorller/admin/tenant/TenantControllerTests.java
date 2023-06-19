/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.tenant.TenantController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
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

import static com.rc.cloud.common.core.util.date.LocalDateTimeUtils.buildTime;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link TenantController} 类
 */
@RcTest
public class TenantControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Resource
    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private TenantService tenantService;

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
    public void getIdByName_success() throws Exception {
        mvc.perform(get("/sys/tenant/get-id-by-name?name=柔川信息")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    public void createTenant_success() throws Exception {
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser123");
        tenantCreateReqVO.setPassword("test_password");
        tenantCreateReqVO.setName("test_tenant_name");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang");
        tenantCreateReqVO.setContactMobile("13777777777");
        tenantCreateReqVO.setPackageId(111L);
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(tenantCreateReqVO);
        mvc.perform(post("/sys/tenant/create")
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
    public void updateTenant_success() throws Exception {
        TenantUpdateReqVO tenantUpdateReqVO = new TenantUpdateReqVO();
        tenantUpdateReqVO.setId(121L);
        tenantUpdateReqVO.setName("test_tenant_name");
        tenantUpdateReqVO.setDomain("https://www.baidu.com");
        tenantUpdateReqVO.setContactName("huang");
        tenantUpdateReqVO.setContactMobile("13777777777");
        tenantUpdateReqVO.setPackageId(111L);
        tenantUpdateReqVO.setStatus(0);
        tenantUpdateReqVO.setAccountCount(10000);
        tenantUpdateReqVO.setExpireTime(buildTime(2033, 2, 2));
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(tenantUpdateReqVO);
        mvc.perform(put("/sys/tenant/update")
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

    // 根据ID删除
    @Test
    public void deleteTenantById_success() throws Exception {
        TenantCreateReqVO tenantCreateReqVO = new TenantCreateReqVO();
        tenantCreateReqVO.setUsername("testuser123");
        tenantCreateReqVO.setPassword("test_password");
        tenantCreateReqVO.setName("test_tenant_name");
        tenantCreateReqVO.setDomain("https://www.baidu.com");
        tenantCreateReqVO.setContactName("huang");
        tenantCreateReqVO.setContactMobile("13777777777");
        tenantCreateReqVO.setPackageId(111L);
        tenantCreateReqVO.setStatus(0);
        tenantCreateReqVO.setAccountCount(10000);
        tenantCreateReqVO.setExpireTime(buildTime(2033, 2, 2));
        Long tenantId = tenantService.createTenant(tenantCreateReqVO);
        mvc.perform(delete("/sys/tenant/" + tenantId)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void getTenantById_success() throws Exception {
        mvc.perform(get("/sys/tenant/get/" + 1)
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("柔川信息"));
    }

    @Test
    public void getTenantPage_success() throws Exception {
        mvc.perform(get("/sys/tenant/page")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(4))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].name").value("zuhu1"));
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