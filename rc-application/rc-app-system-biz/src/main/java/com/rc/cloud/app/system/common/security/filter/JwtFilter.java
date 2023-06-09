package com.rc.cloud.app.system.common.security.filter;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.interfaces.Claim;
import com.rc.cloud.app.system.common.security.cache.TokenStoreCache;
import com.rc.cloud.app.system.common.security.user.UserInfoCommon;
import com.rc.cloud.app.system.common.security.utils.DoubleJWTUtil;
import com.rc.cloud.app.system.model.user.AdminUserDO;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.enums.token.TokenTypeEnum;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Resource
    private TokenStoreCache tokenStoreCache;

    private PermissionService permissionService;

    @Resource
    private DoubleJWTUtil doubleJWTUtil;

    @Value("${rc.jwt.header}")
    private String header;

    @Value("${rc.jwt.prefix}")
    private String prefix;

    @Value("${rc.jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime;

    @Value("${rc.jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime;

    @Value("${rc.jwt.secret}")
    private String secret; // JWT 密钥

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
