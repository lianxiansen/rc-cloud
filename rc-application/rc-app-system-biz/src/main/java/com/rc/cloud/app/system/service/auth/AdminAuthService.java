package com.rc.cloud.app.system.service.auth;

import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.vo.auth.AuthLoginReqVO;
import com.rc.cloud.app.system.vo.auth.AuthLoginRespVO;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 管理后台的认证 Service 接口
 *
 * 提供用户的登录、登出的能力
 *
 * @author 芋道源码
 */
public interface AdminAuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    SysUserDO authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);

    /**
     * 退出登录
     */
//    void logout();

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
//    AuthLoginRespVO refreshToken(String refreshToken);

//    Optional<SysUserDO> findOptionalByUsernameWithAuthorities(String username);

}
