/**
 * @author oliveoil
 * date 2023-06-13 13:23
 */
package com.rc.cloud.app.system.contorller.admin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.app.system.common.cache.RedisCache;
import com.rc.cloud.app.system.common.cache.RedisKeys;
import com.rc.cloud.app.system.common.test.RcTest;
import com.rc.cloud.app.system.controller.admin.user.UserProfileController;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.app.system.vo.captcha.CaptchaVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.common.core.enums.SexEnum;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 关联 {@link UserProfileController} 类
 */
@RcTest
public class UserProfileControllerTests {

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
    public void getUserProfileById_success() throws Exception {
        mvc.perform(get("/sys/user/profile/get")
                        .header("Authorization", "Bearer " + getToken().getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    public void updateUserProfile_success() throws Exception {
        UserProfileUpdateReqVO updateReqVO = new UserProfileUpdateReqVO();
        updateReqVO.setNickname("admin1");
        updateReqVO.setEmail("32737238@qq.com");
        updateReqVO.setMobile("13666666666");
        updateReqVO.setSex(SexEnum.FEMALE.getSex());
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/user/profile/update")
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
    public void updatePassword_success() throws Exception {
        UserProfileUpdatePasswordReqVO updateReqVO = new UserProfileUpdatePasswordReqVO();
        updateReqVO.setOldPassword("123456");
        updateReqVO.setNewPassword("1234567");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateReqVO);
        mvc.perform(put("/sys/user/profile/update-password")
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
