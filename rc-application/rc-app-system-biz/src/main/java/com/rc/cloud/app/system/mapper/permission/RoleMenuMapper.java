package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.system.api.permission.entity.SysRoleMenuDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<SysRoleMenuDO> {


    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, SysRoleMenuDO> {
    }

    default List<SysRoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(SysRoleMenuDO::getRoleId, roleId);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .in(SysRoleMenuDO::getMenuId, menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().eq(SysRoleMenuDO::getMenuId, menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().eq(SysRoleMenuDO::getRoleId, roleId));
    }

    default Set<Long> getMenuIdsByRoleIds(Set<Long> toList) {
        QueryWrapper<SysRoleMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysRoleMenuDO::getMenuId);
        wrapper.lambda().in(SysRoleMenuDO::getRoleId, toList);
        return selectList(wrapper).stream().map(SysRoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

}
