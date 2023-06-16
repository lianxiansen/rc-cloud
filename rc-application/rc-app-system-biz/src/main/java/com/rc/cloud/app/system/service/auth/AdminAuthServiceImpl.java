package com.rc.cloud.app.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.common.security.cache.TokenStoreCache;
import com.rc.cloud.app.system.common.security.utils.DoubleJWTUtil;
import com.rc.cloud.app.system.enums.login.LoginLogTypeEnum;
import com.rc.cloud.app.system.enums.token.TokenTypeEnum;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.user.AdminUserMapper;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.enums.UserTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.enums.GlobalErrorCodeConstants.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * Auth Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;

    @Resource
    private CaptchaService captchaService;
//    @Resource
//    private LoginLogService loginLogService;
    @Resource
    private Validator validator;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private PermissionService permissionService;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${rc.captcha.enable:true}")
    private Boolean captchaEnable;

    @Resource
    private DoubleJWTUtil doubleJWTUtil;

    @Resource
    private TokenStoreCache tokenStoreCache;

    @Override
    public SysUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        SysUserDO user = userService.getUserByUsername(username);
        if (user == null) {
//            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
//            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
//            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);

        // 使用账号密码，进行登录
        SysUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，并记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }


//    private void createLoginLog(Long userId, String username,
//                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
//        // 插入登录日志
//        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
//        reqDTO.setLogType(logTypeEnum.getType());
//        reqDTO.setTraceId(TracerUtils.getTraceId());
//        reqDTO.setUserId(userId);
//        reqDTO.setUserType(getUserType().getValue());
//        reqDTO.setUsername(username);
//        reqDTO.setUserAgent(ServletUtils.getUserAgent());
//        reqDTO.setUserIp(ServletUtils.getClientIP());
//        reqDTO.setResult(loginResult.getResult());
//        loginLogService.createLoginLog(reqDTO);
//        // 更新最后登录时间
//        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
//            userService.updateUserLogin(userId, ServletUtils.getClientIP());
//        }
//    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return;
        }
        // 验证码效验
        boolean flag = captchaService.validate(reqVO.getKey(), reqVO.getCaptcha());
        if (!flag) {
            // 保存登录日志
//            sysLogLoginService.save(loginDTO.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, "验证码错误");
        }
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
//        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        return createDoubleToken(userId, username);
    }

    private AuthLoginRespVO createDoubleToken(Long userId, String username) {
        // 创建访问令牌
        AuthLoginRespVO authLoginRespVO = doubleJWTUtil.generateTokens(userId, username);

        // 保存token到redis
        tokenStoreCache.saveJwtAccessToken(authLoginRespVO.getAccessToken(), username);
        tokenStoreCache.saveJwtRefreshToken(authLoginRespVO.getRefreshToken(), username);
        return authLoginRespVO;
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        // 检查refreshToken是否有效
        SysUserDO sysUserDO = validateRefreshToken(refreshToken);

        // 从缓存中删除access_token和refresh_token
        deleteJwtTokenByUsername(sysUserDO.getUsername());

        // 生成token
        return createDoubleToken(sysUserDO.getId(), sysUserDO.getUsername());
    }

    private SysUserDO validateRefreshToken(String refreshToken) {
        // 检查token是否有效
        if (!doubleJWTUtil.checkToken(refreshToken, TokenTypeEnum.REFRESH_TOKEN.getValue())) {
            throw exception(REFRESH_TOKEN_EXPIRED);
        }

        // 从jwt token中获取用户名
        String username = doubleJWTUtil.getUsernameFromRefreshToken(refreshToken);

        // 验证refreshToken是否在缓存中
        if (!tokenStoreCache.validateHasJwtRefreshToken(refreshToken, username)) {
            throw exception(REFRESH_TOKEN_INVALID);
        }

        // 查询用户信息: 用户不存在或者已被禁用
        return adminUserMapper.findOptionalByUsername(username).orElseThrow(() -> exception(USER_NOT_EXISTS));
    }

    /**
     * 通过用户名删除缓存中的token
     *
     * @param username 用户名
     */
    private void deleteJwtTokenByUsername(String username) {
        // 删除access_token
        tokenStoreCache.deleteJwtAccessTokenByUsername(username);
        // 删除refresh_token
        tokenStoreCache.deleteJwtRefreshTokenByUsername(username);
    }

    @Override
    public Optional<SysUserDO> findOptionalByUsernameWithAuthorities(String username) {
        Optional<SysUserDO> optionalByUsername = adminUserMapper.findOptionalByUsername(username);
        if (!optionalByUsername.isPresent()) {
            return Optional.empty();
        } else {
            SysUserDO sysUserDO = optionalByUsername.get();
            Set<String> userAuthority = permissionService.getPermissionListByUserId(sysUserDO.getId());
            optionalByUsername.ifPresent(adminUserDO1 ->
                    adminUserDO1.setAuthorities(userAuthority));
        }
        return optionalByUsername;
    }

    // HttpServletRequest request

    @Override
    public void logout() {
        // 获取当前用户名
        String username = doubleJWTUtil.getUsernameFromCurrentAccessToken();
        // 从缓存中删除access_token和refresh_token
        deleteJwtTokenByUsername(username);

//        // 保存登录日志
//        sysLogLoginService.save(username, Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
//
//        // 登出
//        if (StrUtil.isNotBlank(token)) {
//            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
//        }
    }

//    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
//        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
//        reqDTO.setLogType(logType);
//        reqDTO.setTraceId(TracerUtils.getTraceId());
//        reqDTO.setUserId(userId);
//        reqDTO.setUserType(userType);
//        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
//            reqDTO.setUsername(getUsername(userId));
//        } else {
//            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
//        }
//        reqDTO.setUserAgent(ServletUtils.getUserAgent());
//        reqDTO.setUserIp(ServletUtils.getClientIP());
//        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
//        loginLogService.createLoginLog(reqDTO);
//    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        SysUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
