///**
// * @author oliveoil
// * date 2023-04-18 14:41
// */
//package com.rc.cloud.app.system.biz.contorller.admin.auth;
//
//import com.rc.cloud.app.system.biz.controller.admin.auth.AuthController;
//import com.rc.cloud.app.system.biz.controller.admin.oauth2.OAuth2OpenController;
//import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * 关联 {@link AuthController} 类
// */
//public class AuthControllerTests extends BaseMockitoUnitTest {
//
//    @InjectMocks
//    private AuthController authController;
//
////    @Test
////    public void get_captcha_success() throws Exception {
////        mvc.perform(get("/sys/auth/captcha"))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(0))
////                .andExpect(jsonPath("$.message").value("success"))
////                .andExpect(jsonPath("$.data.image").isNotEmpty())
////                .andExpect(jsonPath("$.data.key").isNotEmpty());
////    }
//
////    @Test
////    public void login_success() throws Exception {
////        // 准备参数
////        SysAccountLoginDTO login = new SysAccountLoginDTO();
////        login.setUsername("admin_test");
////        login.setPassword("123");
////        login.setKey("xxx");
////        login.setCaptcha("xxx");
////        ObjectMapper mapper = new ObjectMapper();
////        String requestBody = mapper.writerWithDefaultPrettyPrinter()
////                .writeValueAsString(login);
////
////        mvc.perform(post("/sys/auth/login")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(requestBody)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(0))
////                .andExpect(jsonPath("$.message").value("success"))
////                .andExpect(jsonPath("$.data.access_token").isNotEmpty())
////                .andExpect(jsonPath("$.data.refresh_token").isNotEmpty());
////    }
////
////    // sad path：登录时用户名错误
////    @Test
////    public void loginWithErrorUsername_then_throwException() throws Exception {
////        // 准备参数
////        SysAccountLoginDTO login = new SysAccountLoginDTO();
////        login.setUsername("admin111");
////        login.setPassword("123");
////        login.setKey("xxx");
////        login.setCaptcha("xxx");
////        ObjectMapper mapper = new ObjectMapper();
////        String requestBody = mapper.writerWithDefaultPrettyPrinter()
////                .writeValueAsString(login);
////
////        mvc.perform(post("/sys/auth/login")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(requestBody)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isForbidden())
////                .andExpect(jsonPath("$.code").value(10070))
////                .andExpect(jsonPath("$.message").value("用户名或密码错误"));
////    }
////
////    // sad path：登录时密码错误
////    @Test
////    public void loginWithErrorPassword_then_throwException() throws Exception {
////        // 准备参数
////        SysAccountLoginDTO login = new SysAccountLoginDTO();
////        login.setUsername("admin_test");
////        login.setPassword("123321");
////        login.setKey("xxx");
////        login.setCaptcha("xxx");
////        ObjectMapper mapper = new ObjectMapper();
////        String requestBody = mapper.writerWithDefaultPrettyPrinter()
////                .writeValueAsString(login);
////
////        mvc.perform(post("/sys/auth/login")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(requestBody)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isForbidden())
////                .andExpect(jsonPath("$.code").value(10070))
////                .andExpect(jsonPath("$.message").value("用户名或密码错误"));
////    }
////
////    // sad path：登录时验证码错误，涉及到数据库中的一个字段，暂时不测试
//////    @Test
//////    public void loginWithErrorCaptcha_then_throwException() throws Exception {
//////        // 准备参数
//////        SysAccountLoginDTO login = new SysAccountLoginDTO();
//////        login.setUsername("admin");
//////        login.setPassword("123");
//////        login.setKey("xxx");
//////        login.setCaptcha("xxx");
//////        ObjectMapper mapper = new ObjectMapper();
//////        String requestBody = mapper.writerWithDefaultPrettyPrinter()
//////                .writeValueAsString(login);
//////
//////        mvc.perform(post("/sys/auth/login")
//////                        .contentType(MediaType.APPLICATION_JSON)
//////                        .content(requestBody)
//////                        .accept(MediaType.APPLICATION_JSON))
//////                .andDo(print())
//////                .andExpect(status().isForbidden())
//////                .andExpect(jsonPath("$.code").value(10070))
//////                .andExpect(jsonPath("$.message").value("验证码错误"));
//////    }
////
////    @Test
////    public void get_access_token_by_refresh_token_success() throws Exception {
////        SysRefreshTokenDTO refreshTokenDTO = new SysRefreshTokenDTO();
////        refreshTokenDTO.setRefreshToken(getToken().getRefresh_token());
////        ObjectMapper mapper = new ObjectMapper();
////        String requestBody = mapper.writerWithDefaultPrettyPrinter()
////                .writeValueAsString(refreshTokenDTO);
////        // 准备参数
////        MvcResult mvcResult = mvc.perform(post("/sys/auth/token/refresh")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(requestBody)
////                        .accept(MediaType.APPLICATION_JSON)
////                )
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(0))
////                .andExpect(jsonPath("$.message").value("success"))
////                .andExpect(jsonPath("$.data.access_token").isNotEmpty())
////                .andExpect(jsonPath("$.data.refresh_token").isNotEmpty())
////                .andReturn();
////        String contentAsString = mvcResult.getResponse().getContentAsString();
////        System.out.println(contentAsString);
////        Map<String, Object> mapRoot = JSON.parseObject(contentAsString, new TypeReference<>(){});
////        Object data = mapRoot.get("data");
////        String dataStr = JSON.toJSONString(data);
////        Map<String, Object> mapData = JSON.parseObject(dataStr, new TypeReference<>(){});
////        String access_token = (String) mapData.get("access_token");
////        System.out.println(access_token);
////
////        mvc.perform(get("/sys/user/info")
////                        .header("Authorization", "Bearer " + access_token))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(0))
////                .andExpect(jsonPath("$.message").value("success"))
////                .andExpect(jsonPath("$.data.username").value("admin_test"));
////    }
////
////    @Test
////    public void logout_success() throws Exception {
////        mvc.perform(get("/sys/auth/logout")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .header("Authorization", "Bearer " + getToken().getAccess_token())
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.code").value(0))
////                .andExpect(jsonPath("$.message").value("success"));
////    }
////
////    private SysTokenVO getToken() {
////        SysAccountLoginDTO login = new SysAccountLoginDTO();
////        login.setUsername("admin_test");
////        login.setPassword("123");
////        login.setKey("xxx");
////        login.setCaptcha("xxx");
////        return sysAuthService.loginAndReturnDoubleToken(login);
////    }
//}
