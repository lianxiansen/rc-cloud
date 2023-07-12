package com.rc.cloud.app.system.remote;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import com.rc.cloud.app.system.api.user.vo.SysUserInfoVO;
import com.rc.cloud.app.system.model.user.SysUserPO;
import com.rc.cloud.app.system.service.user.AdminUserService;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import com.rc.cloud.common.tenant.core.context.TenantContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 供用户远程调用
 * @author rc@hqf
 * date 2023-07-12 13:34
 */
@RestController
@RequestMapping("/sys/user")
@Validated
public class RemoteUserServerImpl {

    @Resource
    private AdminUserService userService;

    /**
     * 获取指定用户全部信息
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{username}")
    public CodeResult<UserInfo> info(@PathVariable String username) {
        // 硬编码设置租户ID
        TenantContextHolder.setTenantId("1");
        SysUserPO user = userService.getUserByUsername(username);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        UserInfo userInfo = userService.getUserInfo(user);
        return CodeResult.ok(userInfo);
    }

    /**
     * 获取指定用户全部信息
     * @return 用户信息
     */
//    @Inner
    @GetMapping("/info-by-id/{id}")
    public CodeResult<SysUserInfoVO> infoById(@PathVariable String id) {
        // 硬编码设置租户ID
        TenantContextHolder.setTenantId("1");
        SysUserPO user = userService.getUser(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        SysUserInfoVO sysUserVO = new SysUserInfoVO();
        BeanUtils.copyProperties(user, sysUserVO);
        return CodeResult.ok(sysUserVO);
    }

    /**
     * 获取指定用户全部信息
     * @return 用户信息
     */
//    @Inner
    @PostMapping("/info-by-ids")
    public CodeResult<List<SysUserInfoVO>> infoByIds(@RequestBody List<String> ids) {
        // 硬编码设置租户ID
        TenantContextHolder.setTenantId("1");
        List<SysUserPO> users = userService.getUserList(ids);
        List<SysUserInfoVO> sysUserVOS = new ArrayList<>();
        users.forEach(user -> {
            SysUserInfoVO sysUserVO = new SysUserInfoVO();
            BeanUtils.copyProperties(user, sysUserVO);
            sysUserVOS.add(sysUserVO);
        });
        return CodeResult.ok(sysUserVOS);
    }
}
