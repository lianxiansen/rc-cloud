package com.rc.cloud.app.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.rc.cloud.app.system.api.dept.model.SysDeptDO;
import com.rc.cloud.app.system.api.permission.model.SysRoleDO;
import com.rc.cloud.app.system.common.datapermission.core.annotation.DataPermission;
import com.rc.cloud.app.system.convert.user.UserConvert;
import com.rc.cloud.app.system.api.dept.model.SysPostDO;
import com.rc.cloud.app.system.api.user.model.SysUserDO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.service.dept.PostService;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.app.system.vo.user.profile.UserProfileRespVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.rc.cloud.app.system.vo.user.profile.UserProfileUpdateReqVO;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.rc.cloud.common.core.web.util.WebFrameworkUtils.getLoginUserId;

//import static com.rc.cloud.common.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/sys/user/profile")
@Validated
@Slf4j
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

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CodeResult<UserProfileRespVO> profile() {
        // 获得用户基本信息
        SysUserDO user = userService.getUser(getLoginUserId());
        UserProfileRespVO resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<SysRoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        // 获得部门信息
        if (user.getDeptId() != null) {
            SysDeptDO dept = deptService.getDept(user.getDeptId());
            resp.setDept(UserConvert.INSTANCE.convert02(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<SysPostDO> posts = postService.getPostList(user.getPostIds());
            resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
        }
        return CodeResult.ok(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CodeResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return CodeResult.ok(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CodeResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
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
