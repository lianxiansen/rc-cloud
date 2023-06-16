package com.rc.cloud.app.system.controller.admin.auth;

import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.api.user.entity.SysUserDO;
import com.rc.cloud.app.system.convert.auth.AuthConvert;
import com.rc.cloud.app.system.enums.permission.MenuTypeEnum;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.service.auth.AdminAuthService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.auth.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.collection.SetUtils;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.rc.cloud.common.core.web.util.WebFrameworkUtils.getLoginUserId;
import static java.util.Collections.singleton;

@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/sys/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;


    @PostMapping("/login")
    @Operation(summary = "使用账号密码登录")
    public CodeResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO, HttpServletRequest request) {
        return CodeResult.ok(authService.login(reqVO));
    }

    @GetMapping("/logout")
    @Operation(summary = "登出系统")
//    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CodeResult<Boolean> logout() {
        authService.logout();
        return CodeResult.ok(true);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
//    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CodeResult<AuthLoginRespVO> refreshToken(@RequestBody @Valid RefreshTokenVO sysRefreshTokenDTO) {
        return CodeResult.ok(authService.refreshToken(sysRefreshTokenDTO.getRefreshToken()));
    }

    @GetMapping("/get-permission-info")
    @Operation(summary = "获取登录用户的权限信息")
    public CodeResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 获得用户信息
        SysUserDO user = userService.getUser(getLoginUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        List<SysRoleDO> roleList = roleService.getRoleListFromCache(roleIds);
        // 获得菜单列表
        List<SysMenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType(), MenuTypeEnum.BUTTON.getType()),
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 拼接结果返回
        return CodeResult.ok(AuthConvert.INSTANCE.convert(user, roleList, menuList));
    }

    @GetMapping("/list-menus")
    @Operation(summary = "获得登录用户的菜单列表")
    public CodeResult<List<AuthMenuRespVO>> getMenuList() {
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获得用户拥有的菜单列表
        List<SysMenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType()), // 只要目录和菜单类型
                singleton(CommonStatusEnum.ENABLE.getStatus())); // 只要开启的
        // 转换成 Tree 结构返回
        return CodeResult.ok(AuthConvert.INSTANCE.buildMenuTree(menuList));
    }
}
