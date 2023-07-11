package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.system.model.permission.SysRoleMenuPO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<SysRoleMenuPO> {


    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, SysRoleMenuPO> {
    }

    default List<SysRoleMenuPO> selectListByRoleId(String roleId) {
        return selectList(SysRoleMenuPO::getRoleId, roleId);
    }

    default void deleteListByRoleIdAndMenuIds(String roleId, Collection<String> menuIds) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>()
                .eq(SysRoleMenuPO::getRoleId, roleId)
                .in(SysRoleMenuPO::getMenuId, menuIds));
    }

    default void deleteListByMenuId(String menuId) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getMenuId, menuId));
    }

    default void deleteListByRoleId(String roleId) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getRoleId, roleId));
    }

    default Set<String> getMenuIdsByRoleIds(Set<String> toList) {
        QueryWrapper<SysRoleMenuPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysRoleMenuPO::getMenuId);
        wrapper.lambda().in(SysRoleMenuPO::getRoleId, toList);
        return selectList(wrapper).stream().map(SysRoleMenuPO::getMenuId).collect(Collectors.toSet());
    }

}
