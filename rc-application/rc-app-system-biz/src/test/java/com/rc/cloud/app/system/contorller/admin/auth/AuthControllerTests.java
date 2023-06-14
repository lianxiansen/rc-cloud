/**
 * @author oliveoil
 * date 2023-04-18 14:41
 */
package com.rc.cloud.app.system.contorller.admin.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.auth.AuthController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.auth.RefreshTokenVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link AuthController} 类
 */
@RcTest
public class AuthControllerTests {

    @Resource
    private WebApplicationContext context;

    @Resource
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private AdminAuthService authService;

    @Resource
    private RedisCache redisCache;

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
    public void loginWithCaptcha_success() throws Exception {
        CaptchaVO captcha = getCaptcha();
        // 准备参数
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
        login.setKey(captcha.getKey());
        login.setCaptcha(getCaptchaCode(captcha.getKey()));
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(login);

        mvc.perform(post("/sys/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty());
    }

    @Test
    public void testCreatePassword() {
        String encodePassword = passwordEncoder.encode("123456");
        System.out.println(encodePassword);
    }

    // sad path：登录时用户名错误
    @Test
    public void loginWithErrorUsername_then_throwException() throws Exception {
        CaptchaVO captcha = getCaptcha();
        // 准备参数
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin111");
        login.setPassword("123456");
        login.setKey(captcha.getKey());
        login.setCaptcha(getCaptchaCode(captcha.getKey()));
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(login);

        mvc.perform(post("/sys/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002000000))
                .andExpect(jsonPath("$.msg").value("登录失败，账号密码不正确"));
    }

    // sad path：登录时密码错误
    @Test
    public void loginWithErrorPassword_then_throwException() throws Exception {
        CaptchaVO captcha = getCaptcha();
        // 准备参数
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123321");
        login.setKey(captcha.getKey());
        login.setCaptcha(getCaptchaCode(captcha.getKey()));
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(login);

        mvc.perform(post("/sys/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002000000))
                .andExpect(jsonPath("$.msg").value("登录失败，账号密码不正确"));
    }

    // sad path：登录时验证码错误
    @Test
    public void loginWithErrorCaptcha_then_throwException() throws Exception {
        CaptchaVO captcha = getCaptcha();
        // 准备参数
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
        login.setKey(captcha.getKey());
        login.setCaptcha("xxx");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(login);

        mvc.perform(post("/sys/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002000004))
                .andExpect(jsonPath("$.msg").value("验证码不正确，原因：验证码错误"));
    }

    // happy path：根据refreshToken获取accessToken
    @Test
    public void get_accessTokenByRefreshToken_success() throws Exception {
        AuthLoginRespVO authLoginRespVO =  getToken();
        RefreshTokenVO refreshTokenVO = new RefreshTokenVO();
        refreshTokenVO.setRefreshToken(authLoginRespVO.getRefreshToken());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(refreshTokenVO);
        // 准备参数
        MvcResult mvcResult = mvc.perform(post("/sys/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
//        System.out.println(contentAsString);
        Map<String, Object> mapRoot = JSON.parseObject(contentAsString, new TypeReference<Map<String, Object>>(){});
        Object data = mapRoot.get("data");
        String dataStr = JSON.toJSONString(data);
        Map<String, Object> mapData = JSON.parseObject(dataStr, new TypeReference<Map<String, Object>>(){});
        String accessToken = (String) mapData.get("accessToken");
//        System.out.println(accessToken);

        mvc.perform(get("/sys/user/profile/get")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    public void logout_success() throws Exception {
        mvc.perform(get("/sys/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Test
    public void getPermissionInfo_success() throws Exception {
        mvc.perform(get("/sys/auth/get-permission-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.user.nickname").value("芋道源码"));
    }

    @Test
    public void getMenuList_success() throws Exception {
        mvc.perform(get("/sys/auth/list-menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
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
