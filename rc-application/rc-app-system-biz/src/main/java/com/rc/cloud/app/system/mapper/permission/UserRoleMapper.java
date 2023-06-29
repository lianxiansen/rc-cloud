package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.api.dept.entity.SysUserPostDO;
import com.rc.cloud.app.system.api.permission.entity.SysUserRoleDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.Db.saveBatch;

@Mapper
public interface UserRoleMapper extends BaseMapperX<SysUserRoleDO> {

    default List<SysUserRoleDO> selectListByUserId(Long userId) {
        return selectList(SysUserRoleDO::getUserId, userId);
    }

    default Set<Long> selectRoleIdsByUserId(Long userId) {
        QueryWrapper<SysUserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRoleDO::getRoleId);
        wrapper.lambda().eq(SysUserRoleDO::getUserId, userId);
        return selectList(wrapper).stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
    }

    default Set<Long> selectUserIdsByRoleId(Long roleId) {
        QueryWrapper<SysUserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysUserRoleDO::getUserId);
        wrapper.lambda().eq(SysUserRoleDO::getRoleId, roleId);
        return selectList(wrapper).stream().map(SysUserRoleDO::getUserId).collect(Collectors.toSet());
    }

    default void deleteListByUserIdAndRoleIdIds(Long userId, Collection<Long> roleIds) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>()
                .eq(SysUserRoleDO::getUserId, userId)
                .in(SysUserRoleDO::getRoleId, roleIds));
    }

    default void deleteListByUserId(Long userId) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().eq(SysUserRoleDO::getUserId, userId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().eq(SysUserRoleDO::getRoleId, roleId));
    }

    default List<SysUserRoleDO> selectListByRoleIds(Collection<Long> roleIds) {
        return selectList(SysUserRoleDO::getRoleId, roleIds);
    }

    default void deleteBatchByUserIdAndRoleIds(Long userId, Collection<Long> roleIds) {
        delete(new LambdaQueryWrapperX<SysUserRoleDO>()
                .eq(SysUserRoleDO::getUserId, userId)
                .in(SysUserRoleDO::getRoleId, roleIds));
    }

    default void saveUserList(Long roleId, List<Long> userIds) {
        List<SysUserRoleDO> userRoleList = userIds.stream().map(userId -> {
            SysUserRoleDO userRole = new SysUserRoleDO();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).collect(Collectors.toList());
        saveBatch(userRoleList);
    }

    default void deleteByUserIds(Long roleId, List<Long> userIds) {
        delete(new LambdaQueryWrapperX<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId)
                .in(SysUserRoleDO::getUserId, userIds));
    }
}
