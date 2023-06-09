/**
 * @author oliveoil
 * date 2023-04-18 14:41
 */
package com.rc.cloud.app.system.contorller.admin.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.auth.AuthController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
                .andExpect(jsonPath("$.code").value(500))
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
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("登录失败，账号密码不正确"));
    }

    // sad path：登录时验证码错误，涉及到数据库中的一个字段，暂时不测试
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
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("验证码不正确，原因：验证码错误"));
    }

//    @Test
//    public void get_access_token_by_refresh_token_success() throws Exception {
//        SysRefreshTokenDTO refreshTokenDTO = new SysRefreshTokenDTO();
//        refreshTokenDTO.setRefreshToken(getToken().getRefresh_token());
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(refreshTokenDTO);
//        // 准备参数
//        MvcResult mvcResult = mvc.perform(post("/sys/auth/token/refresh")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.access_token").isNotEmpty())
//                .andExpect(jsonPath("$.data.refresh_token").isNotEmpty())
//                .andReturn();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        System.out.println(contentAsString);
//        Map<String, Object> mapRoot = JSON.parseObject(contentAsString, new TypeReference<>(){});
//        Object data = mapRoot.get("data");
//        String dataStr = JSON.toJSONString(data);
//        Map<String, Object> mapData = JSON.parseObject(dataStr, new TypeReference<>(){});
//        String access_token = (String) mapData.get("access_token");
//        System.out.println(access_token);
//
//        mvc.perform(get("/sys/user/info")
//                        .header("Authorization", "Bearer " + access_token))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.username").value("admin_test"));
//    }
//
//    @Test
//    public void logout_success() throws Exception {
//        mvc.perform(get("/sys/auth/logout")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + getToken().getAccess_token())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"));
//    }
//
    private AuthLoginRespVO getToken() {
        AuthLoginReqVO login = new AuthLoginReqVO();
        login.setUsername("admin");
        login.setPassword("123456");
        login.setKey("xxx");
        login.setCaptcha("xxx");
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
