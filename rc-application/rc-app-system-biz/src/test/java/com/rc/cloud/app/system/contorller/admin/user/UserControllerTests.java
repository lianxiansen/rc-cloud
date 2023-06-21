/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.common.test.annotation.RcTest;
import com.rc.cloud.app.system.controller.admin.user.UserController;
//import com.rc.cloud.app.system.service.auth.AdminAuthService;
//import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.user.user.UserCreateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateReqVO;
import com.rc.cloud.app.system.vo.user.user.UserUpdateStatusReqVO;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link UserController} 类
 */
@RcTest
public class UserControllerTests {

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
    public void createUser_success() throws Exception {
        UserCreateReqVO createReqVO = new UserCreateReqVO();
        createReqVO.setUsername("testuser123");
        createReqVO.setNickname("test_nickname");
        createReqVO.setAvatar("www.baidu.com");
        createReqVO.setRemark("备注");
        createReqVO.setDeptId(100L);
        createReqVO.setPostIds(new HashSet<Long>() {{add(1L); add(2L);}});
        createReqVO.setEmail("123232@qq.com");
        createReqVO.setMobile("13777777777");
        createReqVO.setSex(1);
        createReqVO.setPassword("123456");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(createReqVO);
        mvc.perform(post("/sys/user/create")
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
    public void updateUser_success() throws Exception {
        UserUpdateReqVO updateReqVO = new UserUpdateReqVO();
        updateReqVO.setId(1L);
        updateReqVO.setUsername("testuser123");
        updateReqVO.setNickname("test_nickname");
        updateReqVO.setAvatar("www.baidu.com");
        updateReqVO.setRemark("备注");
        updateReqVO.setDeptId(100L);
        updateReqVO.setPostIds(new HashSet<Long>() {{add(1L); add(2L);}});
        updateReqVO.setEmail("123232@qq.com");
        updateReqVO.setMobile("13777777777");
        updateReqVO.setSex(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/user/update")
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
    public void deleteUser_success() throws Exception {
        mvc.perform(delete("/sys/user/1")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void updateUserPassword_success() throws Exception {
        UserUpdatePasswordReqVO updatePasswordReqVO = new UserUpdatePasswordReqVO();
        updatePasswordReqVO.setId(1L);
        updatePasswordReqVO.setPassword("1234567");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updatePasswordReqVO);
        mvc.perform(put("/sys/user/update-password")
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
    public void updateUserStatus_success() throws Exception {
        UserUpdateStatusReqVO updateStatusReqVO = new UserUpdateStatusReqVO();
        updateStatusReqVO.setId(1L);
        updateStatusReqVO.setStatus(1);
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateStatusReqVO);
        mvc.perform(put("/sys/user/update-status")
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
    public void getUserPage_success() throws Exception {
        mvc.perform(get("/sys/user/page")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(9))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list").isNotEmpty())
                .andExpect(jsonPath("$.data.list[0].username").value("goudan"));
    }

    @Test
    public void getUserListAllSimple_success() throws Exception {
        mvc.perform(get("/sys/user/list-all-simple")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].nickname").value("芋道源码"));
    }

    @Test
    public void getUserById_then_success() throws Exception {
        mvc.perform(get("/sys/user/1")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("admin"));
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
