package com.rc.cloud.common.security.utils;

import cn.hutool.core.map.MapUtil;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author oliveoil
 * @description OAuth2 端点工具
 */
@UtilityClass
public class OAuth2EndpointUtils {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    /**
     * 将请求中的参数转换成MultiValueMap类型
     *
     * @param request
     * @return MultiValueMap
     */
    public MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }

    /**
     * 判断是否是授权码验证
     *
     * @param request
     * @return boolean
     */
    public boolean matchesPkceTokenRequest(HttpServletRequest request) {
        return AuthorizationGrantType.AUTHORIZATION_CODE.getValue()
                .equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
                && request.getParameter(OAuth2ParameterNames.CODE) != null
                && request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
    }

    /**
     * 抛出异常
     *
     * @param errorCode     错误码
     * @param parameterName 参数名
     * @param errorUri      uri
     */
    public void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }

    /**
     * 格式化输出token 信息
     *
     * @param authentication 用户认证信息
     * @param claims         扩展信息
     * @return OAuth2AccessTokenResponse
     */
    public OAuth2AccessTokenResponse sendAccessTokenResponse(OAuth2Authorization authentication,
                                                             Map<String, Object> claims) {

        OAuth2AccessToken accessToken = authentication.getAccessToken().getToken();
        OAuth2RefreshToken refreshToken = authentication.getRefreshToken().getToken();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType())
                .scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }

        if (MapUtil.isNotEmpty(claims)) {
            builder.additionalParameters(claims);
        }
        return builder.build();
    }

}
