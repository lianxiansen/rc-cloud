/**
 * @author oliveoil
 * date 2023-04-17 11:12
 */
package com.rc.cloud.app.system.service.user;

import com.rc.cloud.app.system.common.security.user.UserDetail;
import com.rc.cloud.app.system.api.user.model.SysUserDO;
import com.rc.cloud.app.system.service.permission.PermissionService;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SysUserDetailsServiceImpl implements SysUserDetailsService {

    private final PermissionService permissionService;

    @Override
    public UserDetails getUserDetails(SysUserDO sysUserDO) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(sysUserDO.getUsername());
        userDetail.setPassword(sysUserDO.getPassword());
        userDetail.setAvatar(sysUserDO.getAvatar());
        userDetail.setSex(sysUserDO.getSex());
        userDetail.setEmail( sysUserDO.getEmail());
        userDetail.setMobile( sysUserDO.getMobile());
        userDetail.setDeptId( sysUserDO.getDeptId());
        userDetail.setStatus( sysUserDO.getStatus());
        // 账号不可用
        if (sysUserDO.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            userDetail.setEnabled(false);
        }

        // 数据权限范围
        List<Long> dataScopeList = getDataScope(userDetail);
        userDetail.setDataScopeList(dataScopeList);

        // 用户权限列表
        Set<String> authoritySet = permissionService.getPermissionListByUserId(sysUserDO.getId());
        userDetail.setAuthoritySet(authoritySet);
        return userDetail;
    }

    private List<Long> getDataScope(UserDetail userDetail) {
//        Integer dataScope = sysRoleRepository.getDataScopeByUserId(userDetail.getId());
//        if (dataScope == null) {
//            return new ArrayList<>();
//        }
//
//        if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
//            // 全部数据权限，则返回null
//            return null;
//        } else if (dataScope.equals(DataScopeEnum.ORG_AND_CHILD.getValue())) {
//            // 本机构及子机构数据
//            List<Long> dataScopeList = sysOrgService.getSubOrgIdList(userDetail.getOrgId());
//            // 自定义数据权限范围
//            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));
//
//            return dataScopeList;
//        } else if (dataScope.equals(DataScopeEnum.ORG_ONLY.getValue())) {
//            // 本机构数据
//            List<Long> dataScopeList = new ArrayList<>();
//            dataScopeList.add(userDetail.getOrgId());
//            // 自定义数据权限范围
//            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));
//
//            return dataScopeList;
//        } else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
//            // 自定义数据权限范围
//            return sysRoleDataScopeDao.getDataScopeList(userDetail.getId());
//        }

        return new ArrayList<>();
    }
}
