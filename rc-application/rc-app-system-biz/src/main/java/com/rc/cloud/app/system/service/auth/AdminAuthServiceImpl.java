package com.rc.cloud.app.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.rc.cloud.app.system.convert.auth.AuthConvert;
import com.rc.cloud.app.system.mapper.permission.MenuMapper;
import com.rc.cloud.app.system.mapper.user.AdminUserMapper;
import com.rc.cloud.app.system.model.oauth2.OAuth2AccessTokenDO;
import com.rc.cloud.app.system.model.user.AdminUserDO;
import com.rc.cloud.app.system.service.captcha.CaptchaService;
import com.rc.cloud.app.system.service.oauth2.OAuth2TokenService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.auth.*;
import com.rc.cloud.app.system.enums.login.LoginLogTypeEnum;
import com.rc.cloud.app.system.enums.oauth2.OAuth2ClientConstants;
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
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.rc.cloud.common.core.util.servlet.ServletUtils.getClientIP;

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
    private OAuth2TokenService oauth2TokenService;
//    @Resource
//    private SocialUserService socialUserService;
//    @Resource
//    private MemberService memberService;
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

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
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
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，并记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

//    @Override
//    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
//        // 登录场景，验证是否存在
//        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
//            throw exception(AUTH_MOBILE_NOT_EXISTS);
//        }
//        // 发送验证码
//        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
//    }

//    @Override
//    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
//        // 校验验证码
//        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));
//
//        // 获得用户信息
//        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
//        if (user == null) {
//            throw exception(USER_NOT_EXISTS);
//        }
//
//        // 创建 Token 令牌，记录登录日志
//        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
//    }

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

//    @Override
//    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
//        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
//        Long userId = socialUserService.getBindUserId(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
//                reqVO.getCode(), reqVO.getState());
//        if (userId == null) {
//            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
//        }
//
//        // 获得用户
//        AdminUserDO user = userService.getUser(userId);
//        if (user == null) {
//            throw exception(USER_NOT_EXISTS);
//        }
//
//        // 创建 Token 令牌，记录登录日志
//        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
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
//        // 创建访问令牌
//        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
//                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
//        // 构建返回结果
//        return AuthConvert.INSTANCE.convert(accessTokenDO);
        return null;
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public Optional<AdminUserDO> findOptionalByUsernameWithAuthorities(String username) {
        Optional<AdminUserDO> optionalByUsername = adminUserMapper.findOptionalByUsername(username);
        if (optionalByUsername.isPresent()) {
            return Optional.empty();
        } else {
            AdminUserDO adminUserDO = optionalByUsername.get();
            Set<String> userAuthority = permissionService.getPermissionListByUserId(adminUserDO.getId());
            optionalByUsername.ifPresent(adminUserDO1 ->
                    adminUserDO1.setAuthorities(userAuthority));
        }
        return optionalByUsername;
    }



    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
//        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
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
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
