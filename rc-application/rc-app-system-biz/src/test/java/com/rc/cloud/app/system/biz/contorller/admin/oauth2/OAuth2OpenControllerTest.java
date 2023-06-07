package com.rc.cloud.app.system.biz.contorller.admin.oauth2;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import com.rc.cloud.app.system.biz.controller.admin.oauth2.OAuth2OpenController;
import com.rc.cloud.app.system.biz.model.oauth2.OAuth2AccessTokenDO;
import com.rc.cloud.app.system.biz.model.oauth2.OAuth2ApproveDO;
import com.rc.cloud.app.system.biz.model.oauth2.OAuth2ClientDO;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2ApproveService;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2ClientService;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2GrantService;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2TokenService;
import com.rc.cloud.app.system.biz.vo.oauth2.open.OAuth2OpenAccessTokenRespVO;
import com.rc.cloud.app.system.biz.vo.oauth2.open.OAuth2OpenAuthorizeInfoRespVO;
import com.rc.cloud.app.system.biz.vo.oauth2.open.OAuth2OpenCheckTokenRespVO;
import com.rc.cloud.app.system.enums.oauth2.OAuth2GrantTypeEnum;
import com.rc.cloud.common.core.core.KeyValue;
import com.rc.cloud.common.core.enums.UserTypeEnum;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.util.collection.SetUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.test.core.ut.BaseMockitoUnitTest;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.rc.cloud.common.core.util.collection.SetUtils.asSet;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertPojoEquals;
import static com.rc.cloud.common.test.core.util.AssertUtils.assertServiceException;
import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static com.rc.cloud.common.test.core.util.RandomUtils.randomString;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@link OAuth2OpenController} 的单元测试
 *
 * @author 芋道源码
 */
public class OAuth2OpenControllerTest extends BaseMockitoUnitTest {

    @InjectMocks
    private OAuth2OpenController oauth2OpenController;

    @Mock
    private OAuth2GrantService oauth2GrantService;
    @Mock
    private OAuth2ClientService oauth2ClientService;
    @Mock
    private OAuth2ApproveService oauth2ApproveService;
    @Mock
    private OAuth2TokenService oauth2TokenService;

    @Test
    public void testPostAccessToken_authorizationCode() {
        // 准备参数
        String granType = OAuth2GrantTypeEnum.AUTHORIZATION_CODE.getGrantType();
        String code = randomString();
        String redirectUri = randomString();
        String state = randomString();
        HttpServletRequest request = mockRequest("test_client_id", "test_client_secret");
        // mock 方法（client）
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId("test_client_id");
        when(oauth2ClientService.validOAuthClientFromCache(eq("test_client_id"), eq("test_client_secret"), eq(granType), eq(new ArrayList<>()), eq(redirectUri))).thenReturn(oAuth2ClientDO);

        // mock 方法（访问令牌）
        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.offset(LocalDateTime.now(), 30000L, ChronoUnit.MILLIS));
        when(oauth2GrantService.grantAuthorizationCodeForAccessToken(eq("test_client_id"),
                eq(code), eq(redirectUri), eq(state))).thenReturn(oAuth2AccessTokenDO);

        // 调用
        CodeResult<OAuth2OpenAccessTokenRespVO> result = oauth2OpenController.postAccessToken(request, granType,
                code, redirectUri, state, null, null, null, null);
        // 断言
        assertEquals(200, result.getCode());
        assertPojoEquals(oAuth2AccessTokenDO, result.getData());
        assertTrue(ObjectUtils.equalsAny(result.getData().getExpiresIn(), 29L, 30L));  // 执行过程会过去几毫秒
    }

    @Test
    public void testPostAccessToken_password() {
        // 准备参数
        String granType = OAuth2GrantTypeEnum.PASSWORD.getGrantType();
        String username = randomString();
        String password = randomString();
        String scope = "write read";
        HttpServletRequest request = mockRequest("test_client_id", "test_client_secret");
        // mock 方法（client）
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId("test_client_id");
        when(oauth2ClientService.validOAuthClientFromCache(eq("test_client_id"), eq("test_client_secret"),
                eq(granType), eq(Lists.newArrayList("write", "read")), isNull())).thenReturn(oAuth2ClientDO);

        // mock 方法（访问令牌）
        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.offset(LocalDateTime.now(), 30000L, ChronoUnit.MILLIS));
        when(oauth2GrantService.grantPassword(eq(username), eq(password), eq("test_client_id"),
                eq(Lists.newArrayList("write", "read")))).thenReturn(oAuth2AccessTokenDO);

        // 调用
        CodeResult<OAuth2OpenAccessTokenRespVO> result = oauth2OpenController.postAccessToken(request, granType,
                null, null, null, username, password, scope, null);
        // 断言
        assertEquals(200, result.getCode());
        assertPojoEquals(oAuth2AccessTokenDO, result.getData());
        assertTrue(ObjectUtils.equalsAny(result.getData().getExpiresIn(), 29L, 30L));  // 执行过程会过去几毫秒
    }

    @Test
    public void testPostAccessToken_refreshToken() {
        // 准备参数
        String granType = OAuth2GrantTypeEnum.REFRESH_TOKEN.getGrantType();
        String refreshToken = randomString();
        String password = randomString();
        HttpServletRequest request = mockRequest("test_client_id", "test_client_secret");
        // mock 方法（client）
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId("test_client_id");
        when(oauth2ClientService.validOAuthClientFromCache(eq("test_client_id"), eq("test_client_secret"),
                eq(granType), eq(Lists.newArrayList()), isNull())).thenReturn(oAuth2ClientDO);

        // mock 方法（访问令牌）
        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.offset(LocalDateTime.now(), 30000L, ChronoUnit.MILLIS));
        when(oauth2GrantService.grantRefreshToken(eq(refreshToken), eq("test_client_id"))).thenReturn(oAuth2AccessTokenDO);

        // 调用
        CodeResult<OAuth2OpenAccessTokenRespVO> result = oauth2OpenController.postAccessToken(request, granType,
                null, null, null, null, password, null, refreshToken);
        // 断言
        assertEquals(200, result.getCode());
        assertPojoEquals(oAuth2AccessTokenDO, result.getData());
        assertTrue(ObjectUtils.equalsAny(result.getData().getExpiresIn(), 29L, 30L));  // 执行过程会过去几毫秒
    }

    @Test
    public void testPostAccessToken_implicit() {
        // 调用，并断言
        assertServiceException(() -> oauth2OpenController.postAccessToken(null,
                        OAuth2GrantTypeEnum.IMPLICIT.getGrantType(), null, null, null,
                        null, null, null, null),
                new ErrorCode(400, "Token 接口不支持 implicit 授权模式"));
    }

    @Test
    public void testRevokeToken() {
        // 准备参数
        HttpServletRequest request = mockRequest("demo_client_id", "demo_client_secret");
        String token = randomString();
        // mock 方法（client）
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId("demo_client_id");
        when(oauth2ClientService.validOAuthClientFromCache(eq("demo_client_id"),
                eq("demo_client_secret"), isNull(), isNull(), isNull())).thenReturn(oAuth2ClientDO);
        // mock 方法（移除）
        when(oauth2GrantService.revokeToken(eq("demo_client_id"), eq(token))).thenReturn(true);

        // 调用
        CodeResult<Boolean> result = oauth2OpenController.revokeToken(request, token);
        // 断言
        assertEquals(200, result.getCode());
        assertTrue(result.getData());
    }

    @Test
    public void testCheckToken() {
        // 准备参数
        HttpServletRequest request = mockRequest("demo_client_id", "demo_client_secret");
        String token = randomString();
        // mock 方法
        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
        oAuth2AccessTokenDO.setUserType(UserTypeEnum.ADMIN.getValue());
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.of(1653485731195L));
        when(oauth2TokenService.checkAccessToken(eq(token))).thenReturn(oAuth2AccessTokenDO);

        // 调用
        CodeResult<OAuth2OpenCheckTokenRespVO> result = oauth2OpenController.checkToken(request, token);
        // 断言
        assertEquals(200, result.getCode());
        assertPojoEquals(oAuth2AccessTokenDO, result.getData());
        assertEquals(1653485731L, result.getData().getExp()); // 执行过程会过去几毫秒
    }

    @Test
    public void testAuthorize() {
        // 准备参数
        String clientId = randomString();
        // mock 方法（client）
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId("demo_client_id");
        oAuth2ClientDO.setScopes(ListUtil.toList("read", "write", "all"));
        when(oauth2ClientService.validOAuthClientFromCache(eq(clientId))).thenReturn(oAuth2ClientDO);
        // mock 方法（approve）
        OAuth2ApproveDO oAuth2ApproveDO1 = randomPojo(OAuth2ApproveDO.class);
        oAuth2ApproveDO1.setScope("read");
        oAuth2ApproveDO1.setApproved(true);
        OAuth2ApproveDO oAuth2ApproveDO2 = randomPojo(OAuth2ApproveDO.class);
        oAuth2ApproveDO2.setScope("write");
        oAuth2ApproveDO2.setApproved(false);
        List<OAuth2ApproveDO> approves = asList(
                oAuth2ApproveDO1, oAuth2ApproveDO2);
        when(oauth2ApproveService.getApproveList(isNull(), eq(UserTypeEnum.ADMIN.getValue()), eq(clientId))).thenReturn(approves);

        // 调用
        CodeResult<OAuth2OpenAuthorizeInfoRespVO> result = oauth2OpenController.authorize(clientId);
        // 断言
        assertEquals(200, result.getCode());
        assertPojoEquals(oAuth2ClientDO, result.getData().getClient());
        assertEquals(new KeyValue<>("read", true), result.getData().getScopes().get(0));
        assertEquals(new KeyValue<>("write", false), result.getData().getScopes().get(1));
        assertEquals(new KeyValue<>("all", false), result.getData().getScopes().get(2));
    }

    @Test
    public void testApproveOrDeny_grantTypeError() {
        // 调用，并断言
        assertServiceException(() -> oauth2OpenController.approveOrDeny(randomString(), null,
                        null, null, null, null),
                new ErrorCode(400, "response_type 参数值只允许 code 和 token"));
    }

    @Test // autoApprove = true，但是不通过
    public void testApproveOrDeny_autoApproveNo() {
        // 准备参数
        String responseType = "code";
        String clientId = randomString();
        String scope = "{\"read\": true, \"write\": false}";
        String redirectUri = randomString();
        String state = randomString();
        // mock 方法
        OAuth2ClientDO client = randomPojo(OAuth2ClientDO.class);
        when(oauth2ClientService.validOAuthClientFromCache(eq(clientId), isNull(), eq("authorization_code"),
                eq(asSet("read", "write")), eq(redirectUri))).thenReturn(client);

        // 调用
        CodeResult<String> result = oauth2OpenController.approveOrDeny(responseType, clientId,
                scope, redirectUri, true, state);
        // 断言
        assertEquals(200, result.getCode());
        assertNull(result.getData());
    }

    @Test // autoApprove = false，但是不通过
    public void testApproveOrDeny_ApproveNo() {
        // 准备参数
        String responseType = "token";
        String clientId = randomString();
        String scope = "{\"read\": true, \"write\": false}";
        String redirectUri = "https://www.iocoder.cn";
        String state = "test";
        // mock 方法
        OAuth2ClientDO client = randomPojo(OAuth2ClientDO.class);
        when(oauth2ClientService.validOAuthClientFromCache(eq(clientId), isNull(), eq("implicit"),
                eq(asSet("read", "write")), eq(redirectUri))).thenReturn(client);

        // 调用
        CodeResult<String> result = oauth2OpenController.approveOrDeny(responseType, clientId,
                scope, redirectUri, false, state);
        // 断言
        assertEquals(200, result.getCode());
        assertEquals("https://www.iocoder.cn#error=access_denied&error_description=User%20denied%20access&state=test", result.getData());
    }

    @Test // autoApprove = true，通过 + token
    public void testApproveOrDeny_autoApproveWithToken() {
        // 准备参数
        String responseType = "token";
        String clientId = randomString();
        String scope = "{\"read\": true, \"write\": false}";
        String redirectUri = "https://www.iocoder.cn";
        String state = "test";
        // mock 方法（client)
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId(clientId);
        oAuth2ClientDO.setAdditionalInformation(null);
        when(oauth2ClientService.validOAuthClientFromCache(eq(clientId), isNull(), eq("implicit"),
                eq(asSet("read", "write")), eq(redirectUri))).thenReturn(oAuth2ClientDO);
        // mock 方法（场景一）
        when(oauth2ApproveService.checkForPreApproval(isNull(), eq(UserTypeEnum.ADMIN.getValue()),
                eq(clientId), eq(SetUtils.asSet("read", "write")))).thenReturn(true);
        // mock 方法（访问令牌）
        OAuth2AccessTokenDO oAuth2AccessTokenDO = randomPojo(OAuth2AccessTokenDO.class);
        oAuth2AccessTokenDO.setAccessToken("test_access_token");
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTimeUtil.offset(LocalDateTime.now(), 30010L, ChronoUnit.MILLIS));
        when(oauth2GrantService.grantImplicit(isNull(), eq(UserTypeEnum.ADMIN.getValue()),
                eq(clientId), eq(ListUtil.toList("read")))).thenReturn(oAuth2AccessTokenDO);

        // 调用
        CodeResult<String> result = oauth2OpenController.approveOrDeny(responseType, clientId,
                scope, redirectUri, true, state);
        // 断言
        assertEquals(200, result.getCode());
        assertEquals("https://www.iocoder.cn#access_token=test_access_token&token_type=bearer&state=test&expires_in=30&scope=read", result.getData());
    }

    @Test // autoApprove = false，通过 + code
    public void testApproveOrDeny_approveWithCode() {
        // 准备参数
        String responseType = "code";
        String clientId = randomString();
        String scope = "{\"read\": true, \"write\": false}";
        String redirectUri = "https://www.iocoder.cn";
        String state = "test";
        // mock 方法（client)
        OAuth2ClientDO oAuth2ClientDO = randomPojo(OAuth2ClientDO.class);
        oAuth2ClientDO.setClientId(clientId);
        oAuth2ClientDO.setAdditionalInformation(null);
        when(oauth2ClientService.validOAuthClientFromCache(eq(clientId), isNull(), eq("authorization_code"),
                eq(asSet("read", "write")), eq(redirectUri))).thenReturn(oAuth2ClientDO);
        // mock 方法（场景二）
        when(oauth2ApproveService.updateAfterApproval(isNull(), eq(UserTypeEnum.ADMIN.getValue()), eq(clientId),
                eq(MapUtil.builder(new LinkedHashMap<String, Boolean>()).put("read", true).put("write", false).build())))
                .thenReturn(true);
        // mock 方法（访问令牌）
        String authorizationCode = "test_code";
        when(oauth2GrantService.grantAuthorizationCodeForCode(isNull(), eq(UserTypeEnum.ADMIN.getValue()),
                eq(clientId), eq(ListUtil.toList("read")), eq(redirectUri), eq(state))).thenReturn(authorizationCode);

        // 调用
        CodeResult<String> result = oauth2OpenController.approveOrDeny(responseType, clientId,
                scope, redirectUri, false, state);
        // 断言
        assertEquals(200, result.getCode());
        assertEquals("https://www.iocoder.cn?code=test_code&state=test", result.getData());
    }

    private HttpServletRequest mockRequest(String clientId, String secret) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(eq("client_id"))).thenReturn(clientId);
        when(request.getParameter(eq("client_secret"))).thenReturn(secret);
        return request;
    }

}
