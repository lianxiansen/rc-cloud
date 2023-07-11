package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysUserRolePO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.Db.saveBatch;

@Mapper
public interface UserRoleMapper extends BaseMapperX<SysUserRolePO> {

    default List<SysUserRolePO> selectListByUserId(String userId) {
        return selectList(SysUserRolePO::getUserId, userId);
    }

    default Set<String> selectRoleIdsByUserId(String userId) {
        QueryWrapper<SysUserRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRolePO::getRoleId);
        wrapper.lambda().eq(SysUserRolePO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserRolePO::getRoleId).collect(Collectors.toSet());
    }

    default Set<String> selectUserIdsByRoleId(String roleId) {
        QueryWrapper<SysUserRolePO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRolePO::getUserId);
        wrapper.lambda().eq(SysUserRolePO::getRoleId, roleId);
        return selectList(wrapper).stream().map(SysUserRolePO::getUserId).collect(Collectors.toSet());
    }

    default void deleteListByUserIdAndRoleIdIds(String userId, Collection<String> roleIds) {
        delete(new LambdaQueryWrapper<SysUserRolePO>()
                .eq(SysUserRolePO::getUserId, userId)
                .in(SysUserRolePO::getRoleId, roleIds));
    }

    default void deleteListByUserId(String userId) {
        delete(new LambdaQueryWrapper<SysUserRolePO>().eq(SysUserRolePO::getUserId, userId));
    }

    default void deleteListByRoleId(String roleId) {
        delete(new LambdaQueryWrapper<SysUserRolePO>().eq(SysUserRolePO::getRoleId, roleId));
    }

    default List<SysUserRolePO> selectListByRoleIds(Collection<String> roleIds) {
        return selectList(SysUserRolePO::getRoleId, roleIds);
    }

    default void deleteBatchByUserIdAndRoleIds(String userId, Collection<String> roleIds) {
        delete(new LambdaQueryWrapperX<SysUserRolePO>()
                .eq(SysUserRolePO::getUserId, userId)
                .in(SysUserRolePO::getRoleId, roleIds));
    }

    default void saveUserList(String roleId, List<String> userIds) {
        List<SysUserRolePO> userRoleList = userIds.stream().map(userId -> {
            SysUserRolePO userRole = new SysUserRolePO();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).collect(Collectors.toList());
        saveBatch(userRoleList);
    }

    default void deleteByUserIds(String roleId, List<String> userIds) {
        delete(new LambdaQueryWrapperX<SysUserRolePO>()
                .eq(SysUserRolePO::getRoleId, roleId)
                .in(SysUserRolePO::getUserId, userIds));
    }
}
