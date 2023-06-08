package com.rc.cloud.app.system.biz.common.security.filter;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.interfaces.Claim;
import com.rc.cloud.app.system.biz.common.security.cache.TokenStoreCache;
import com.rc.cloud.app.system.biz.common.security.user.UserInfoCommon;
import com.rc.cloud.app.system.biz.common.security.utils.DoubleJWTUtil;
import com.rc.cloud.app.system.biz.model.user.AdminUserDO;
import com.rc.cloud.app.system.biz.service.permission.PermissionService;
import com.rc.cloud.app.system.enums.token.TokenTypeEnum;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 用于 JWT Token 形式的请求过滤器
 */
//@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenStoreCache tokenStoreCache;

    @Autowired
    private DoubleJWTUtil doubleJWTUtil;

    @Autowired
    private PermissionService permissionService;

    private final String header = "Authorization"; // HTTP 报头的认证字段的 key
    private final String prefix = "Bearer "; // HTTP 报头的认证字段的值的前缀
    @Min(5L)
    private final long accessTokenExpireTime = 60L; // Access Token 过期时间，单位秒

    @Min(3600L)
    private final long refreshTokenExpireTime = 30 * 24 * 3600L; // Refresh Token 过期时间，单位秒

    private final String secret = "x88Wf09ger56t837gf89nk390rU17c5Vbe8beod7d8d3e695*4"; // JWT 密钥

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(checkJwtTokenInHeader(request) && validateJwtToken(request) && checkJwtTokenInCache(request)) {
            String jwtToken = request.getHeader(header).replace(prefix, "");
            // 解析token
            Map<String, Claim> stringClaimMap = doubleJWTUtil.decodeAccessToken(jwtToken);
            if (stringClaimMap == null) {
                SecurityContextHolder.clearContext();
            } else {
                // 获取用户名
                String username = stringClaimMap.get("username").asString();
                setupSpringAuthentication(username);
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setupSpringAuthentication(String username) {
        // 通过用户名获取用户权限
        Optional<AdminUserDO> adminUserOptional = permissionService.findOptionalByUsernameWithAuthorities(username);
        if (adminUserOptional.isPresent()) {
            throw exception(10040, "Token 无效");
        }
        AdminUserDO adminUserDO = adminUserOptional.get();
        UserInfoCommon userInfoCommon = new UserInfoCommon();
        BeanUtil.copyProperties(adminUserDO, userInfoCommon);

        // 设置 Spring Security 认证
        val authentication = new UsernamePasswordAuthenticationToken(userInfoCommon, null,
                adminUserOptional.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 检查 JWT Token 是否在 HTTP 报头中
     *
     * @param req HTTP 请求
     * @return 是否有 JWT Token
     */
    private boolean checkJwtTokenInHeader(HttpServletRequest req) {
        String authenticationHeader = req.getHeader(header);
        return authenticationHeader != null && authenticationHeader.startsWith(prefix);
    }

    /**
     * 验证 JWT Token 是否有效
     *
     * @param req HTTP 请求
     * @return JWT Token 是否有效
     */
    private boolean validateJwtToken(HttpServletRequest req) {
        String jwtToken = req.getHeader(header).replace(prefix, "");
        return doubleJWTUtil.checkToken(jwtToken, TokenTypeEnum.ACCESS_TOKEN.getValue());
    }

    /**
     * 检查 JWT Token 是否在缓存中
     *
     * @param request HTTP 请求
     * @return JWT Token 是否在缓存中
     */
    private boolean checkJwtTokenInCache(HttpServletRequest request) {
        String jwtToken = request.getHeader(header).replace(prefix, "");
        String username = doubleJWTUtil.getUsernameFromAccessToken(jwtToken);
        return tokenStoreCache.validateHasJwtAccessToken(jwtToken, username);
    }
}
