package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.api.permission.entity.SysUserRoleDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperX<SysUserRoleDO> {

    default List<SysUserRoleDO> selectListByUserId(Long userId) {
        return selectList(SysUserRoleDO::getUserId, userId);
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

}
