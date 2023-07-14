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

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色菜单关联表
 */
@Mapper
public interface RoleMenuMapper extends BaseMapperX<SysRoleMenuPO> {


    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, SysRoleMenuPO> {
    }

    /**
     * 根据角色id查询角色菜单列表
     *
     * @param roleId 角色id
     * @return 角色菜单列表
     */
    default List<SysRoleMenuPO> selectListByRoleId(String roleId) {
        return selectList(SysRoleMenuPO::getRoleId, roleId);
    }

    /**
     * 根据角色id列表和菜单id列表删除角色菜单关联关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     */
    default void deleteListByRoleIdAndMenuIds(String roleId, Collection<String> menuIds) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getRoleId, roleId).in(SysRoleMenuPO::getMenuId, menuIds));
    }

    /**
     * 根据菜单id删除角色菜单关联关系
     *
     * @param menuId 菜单id
     */
    default void deleteListByMenuId(String menuId) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getMenuId, menuId));
    }

    /**
     * 根据角色id删除角色菜单关联关系
     *
     * @param roleId 角色id
     */
    default void deleteListByRoleId(String roleId) {
        delete(new LambdaQueryWrapper<SysRoleMenuPO>().eq(SysRoleMenuPO::getRoleId, roleId));
    }

    /**
     * 根据角色id列表查询菜单id列表
     *
     * @param toList 角色id列表
     * @return 菜单id列表
     */
    default Set<String> getMenuIdsByRoleIds(Set<String> toList) {
        QueryWrapper<SysRoleMenuPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysRoleMenuPO::getMenuId);
        wrapper.lambda().in(SysRoleMenuPO::getRoleId, toList);
        return selectList(wrapper).stream().map(SysRoleMenuPO::getMenuId).collect(Collectors.toSet());
    }

}
