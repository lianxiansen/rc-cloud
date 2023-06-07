///**
// * @author oliveoil
// * date 2023-06-07 09:12
// */
//package com.rc.cloud.app.system.biz.contorller.admin.captcha;
//
//import com.rc.cloud.app.system.biz.common.test.RcTest;
//import com.rc.cloud.app.system.biz.controller.admin.captcha.CaptchaController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
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
// * 针对 {@link CaptchaController}的单元测试
// */
//@RcTest
//public class CaptchaControllerTests {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mvc;
//
//    @Qualifier("springSecurityFilterChain")
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    public void get_captcha_success() throws Exception {
//        mvc.perform(get("/sys/auth/captcha"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(0))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andExpect(jsonPath("$.data.image").isNotEmpty())
//                .andExpect(jsonPath("$.data.key").isNotEmpty());
//    }
//
//    @Test
//    public void testGetCaptcha_success() {
//        // 准备参数
////        String granType = OAuth2GrantTypeEnum.AUTHORIZATION_CODE.getGrantType();
////        String code = randomString();
////        String redirectUri = randomString();
////        String state = randomString();
////        HttpServletRequest request = mockRequest("test_client_id", "test_client_secret");
////        // mock 方法（client）
////        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
////        oAuth2ClientDO.setClientId("test_client_id");
////        when(oauth2ClientService.validOAuthClientFromCache(eq("test_client_id"), eq("test_client_secret"), eq(granType), eq(new ArrayList<>()), eq(redirectUri))).thenReturn(oAuth2ClientDO);
////
////        // mock 方法（访问令牌）
////        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
////        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.offset(LocalDateTime.now(), 30000L, ChronoUnit.MILLIS));
////        when(oauth2GrantService.grantAuthorizationCodeForAccessToken(eq("test_client_id"),
////                eq(code), eq(redirectUri), eq(state))).thenReturn(oAuth2AccessTokenDO);
////
////        // 调用
////        CodeResult<OAuth2OpenAccessTokenRespVO> result = oauth2OpenController.postAccessToken(request, granType,
////                code, redirectUri, state, null, null, null, null);
////        // 断言
////        assertEquals(200, result.getCode());
////        assertPojoEquals(oAuth2AccessTokenDO, result.getData());
////        assertTrue(ObjectUtils.equalsAny(result.getData().getExpiresIn(), 29L, 30L));  // 执行过程会过去几毫秒
//    }
//
//}
