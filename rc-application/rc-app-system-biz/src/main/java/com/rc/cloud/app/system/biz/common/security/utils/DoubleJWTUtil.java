package com.rc.cloud.app.system.biz.common.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rc.cloud.app.system.biz.vo.token.TokenVO;
import com.rc.cloud.app.system.enums.token.TokenTypeEnum;
import com.rc.cloud.common.core.util.HttpContextUtils;
import com.rc.cloud.common.core.util.date.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

public class DoubleJWTUtil {

    private final String header = "Authorization"; // HTTP 报头的认证字段的 key
    private final String prefix = "Bearer "; // HTTP 报头的认证字段的值的前缀

    /**
     * access_token 过期时间
     */
    private long accessExpire;
    /**
     * refresh_token 过期时间
     */
    private long refreshExpire;
    /**
     * 签名算法
     */
    private Algorithm algorithm;
    /**
     * access_token 验证器
     */
    private JWTVerifier accessVerifier;
    /**
     * refresh_token 验证器
     */
    private JWTVerifier refreshVerifier;
    /**
     * token 构建器
     */
    private JWTCreator.Builder builder;



    /**
     * 初始化构建器和验证器，通过签名算法
     */
    public DoubleJWTUtil(Algorithm algorithm, long accessExpire, long refreshExpire) {
        algorithm = algorithm;
        accessExpire = accessExpire;
        refreshExpire = refreshExpire;
        initBuilderAndVerifier();
    }

    /**
     * 初始化构建器和验证器，通过密钥
     */
    public  DoubleJWTUtil(String secret, long accessExpire, long refreshExpire) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    /**
     * 生成token，id为long类型
     * @param tokenType token类型
     * @param id 用户id
     * @param username 用户名
     * @param expire 过期时间
     * @return token
     */
    public String generateToken(String tokenType, long id, String username, long expire) {
        Date expireDate = DateUtils.getDurationDate(expire);
        return this.builder
                .withClaim("type", tokenType)
                .withClaim("id", id)
                .withClaim("username", username)
                .withExpiresAt(expireDate).sign(this.algorithm);
    }

    /**
     * 生成token，id为String类型
     * @param tokenType token类型
     * @param id 用户id
     * @param username 用户名
     * @param expire 过期时间
     * @return token
     */
    public String generateToken(String tokenType, String id, String username, long expire) {
        Date expireDate = DateUtils.getDurationDate(expire);
        return this.builder
                .withClaim("type", tokenType)
                .withClaim("id", id)
                .withClaim("username", username)
                .withExpiresAt(expireDate).sign(this.algorithm);
    }

    /**
     * 解析access_token
     * @param token access_token
     * @return token信息
     */
    public Map<String, Claim> decodeAccessToken(String token) {
        DecodedJWT jwt = this.accessVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), TokenTypeEnum.ACCESS_TOKEN.getValue());
        return jwt.getClaims();
    }
    /**
     * 解析refresh_token
     * @param token refresh_token
     * @return token信息
     */
    public Map<String, Claim> decodeRefreshToken(String token) {
        DecodedJWT jwt = this.refreshVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), TokenTypeEnum.REFRESH_TOKEN.getValue());
        return jwt.getClaims();
    }

    /**
     * 检查token type
     * @param type token type
     * @param accessType access_token or refresh_token
     */
    private void checkTokenType(String type, String accessType) {
        if (type == null || !type.equals(accessType)) {
            throw new InvalidClaimException("token type is invalid");
        }
    }

    /**
     * 检查token是否过期
     */
    private void checkTokenExpired(Date expiresAt) {
        long now = System.currentTimeMillis();
        if (expiresAt.getTime() < now) {
            throw new TokenExpiredException("token is expired");
        }
    }

    /**
     * 生成双令牌，id为long类型
     * @param id 用户id
     * @param username 用户名
     * @return 双令牌
     */
    public TokenVO generateSysTokensWithoutRealm(long id, String username) {
        String access = this.generateToken("access", id, username, this.accessExpire);
        String refresh = this.generateToken("refresh", id, username, this.refreshExpire);
        return new TokenVO(access, refresh);
    }

    /**
     * 生成双令牌，id为String类型
     * @param id 用户id
     * @param username 用户名
     * @return 双令牌
     */
    public TokenVO generateSysTokensRealm(String id, String username) {
        String access = this.generateToken("access", id, username, this.accessExpire);
        String refresh = this.generateToken("refresh", id, username, this.refreshExpire);
        return new TokenVO(access, refresh);
    }

    /**
     * 通过access_token获取用户名
     * @param accessToken access_token
     * @return 双令牌
     */
    public String getUsernameFromAccessToken(String accessToken) {
        return this.decodeAccessToken(accessToken).get("username").asString();
    }

    /**
     * 获取当前请求的用户名
     * @return 用户名
     */
    public String getUsernameFromCurrentAccessToken() {
        return getUsernameFromAccessToken(getCurrentAccessToken());
    }

    /**
     * 通过access_token获取用户id
     * @param accessToken access_token
     * @return 用户id
     */
    public Long getIdFromAccessToken(String accessToken) {
        return this.decodeAccessToken(accessToken).get("id").asLong();
    }

    /**
     * 获取当前请求的用户id
     * @return 用户id
     */
    public Long getIdFromCurrentAccessToken() {
        return getIdFromAccessToken(getCurrentAccessToken());
    }

    /**
     * 通过refresh_token获取用户名
     * @param refreshToken refresh_token
     * @return 用户名
     */
    public String getUsernameFromRefreshToken(String refreshToken) {
        return this.decodeRefreshToken(refreshToken).get("username").asString();
    }

    /**
     * 获取当前请求的accessToken
     * @return accessToken
     */
    public String getCurrentAccessToken() {
        // 获取accessToken
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            throw exception(10000, "token不能为空");
        }
        return request.getHeader(header).replace(prefix, "");
    }

    /**
     * 检查token是否有效
     * @param token token
     * @param tokenType token类型
     * @return 是否有效
     */
    public boolean checkToken(String token, String tokenType) {
        Map<String, Claim> claims;
        try {
            if (Objects.equals(tokenType, TokenTypeEnum.ACCESS_TOKEN.getValue())) {
                claims = decodeAccessToken(token);
            }
            else {
                claims = decodeRefreshToken(token);
            }
        } catch (TokenExpiredException | AlgorithmMismatchException | SignatureVerificationException |
                 JWTDecodeException | InvalidClaimException e) {
            return false;
        }
        return claims != null;
    }

    /**
     * 初始化构建器和验证器
     */
    public JWTVerifier getAccessVerifier() {
        return this.accessVerifier;
    }

    /**
     * 获取refresh_token验证器
     * @return 验证器
     */
    public JWTVerifier getRefreshVerifier() {
        return this.refreshVerifier;
    }

    /**
     * 获取构建器
     * @return 构建器
     */
    public JWTCreator.Builder getBuilder() {
        return this.builder;
    }

    /**
     * 获取签名算法
     * @return 签名算法
     */
    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    /**
     * 获取access_token过期时间
     * @return 过期时间
     */
    public Long getAccessExpire() {
        return this.accessExpire;
    }

    /**
     * 获取refresh_token过期时间
     * @return 过期时间
     */
    public Long getRefreshExpire() {
        return this.refreshExpire;
    }

    /**
     * 初始化构建器和验证器
     */
    private void initBuilderAndVerifier() {
        this.accessVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.accessExpire).build();
        this.refreshVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.refreshExpire).build();
        this.builder = JWT.create();
    }
}
