package com.rc.cloud.app.system.controller.admin.v1.user;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.user.profile.UserProfileRespVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 用户个人中心
 */
@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/admin/user/profile")
@Validated
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    /**
     * 获得登录用户信息
     *
     * @param authentication 登录用户信息
     * @return 用户信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
//    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CodeResult<UserProfileRespVO> profile(Authentication authentication) {
        // 获得用户基本信息
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        UserProfileRespVO resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<SysRolePO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        // 获得部门信息
        if (user.getDeptId() != null) {
            SysDeptPO dept = deptService.getDept(user.getDeptId());
            resp.setDept(UserConvert.INSTANCE.convert02(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<SysPostPO> posts = postService.getPostList(user.getPostIds());
            resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
        }
        return CodeResult.ok(resp);
    }

    /**
     * 修改用户个人信息
     *
     * @param reqVO 用户信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CodeResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        userService.updateUserProfile(user.getId(), reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 修改用户个人密码
     *
     * @param reqVO 密码信息
     * @return 是否成功
     */
    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CodeResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        String username = SecurityUtils.getUsername();
        Optional<SysUserPO> optionalByUsername = userService.findOptionalByUsername(username);
        SysUserPO user = optionalByUsername.orElseThrow(() -> exception(USER_NOT_EXISTS));
        userService.updateUserPassword(user.getId(), reqVO);
        return CodeResult.ok(true);
    }

//    @RequestMapping(value = "/update-avatar", method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
//    @Operation(summary = "上传用户个人头像")
//    public CodeResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
//        if (file.isEmpty()) {
//            throw exception(FILE_IS_EMPTY);
//        }
//        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
//        return success(avatar);
//    }

}
