package com.rc.cloud.app.system.biz.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.system.biz.model.permission.RoleMenuDO;
import com.rc.cloud.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenuDO> {

    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, RoleMenuDO> {
    }

    default List<RoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(RoleMenuDO::getRoleId, roleId);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new LambdaQueryWrapper<RoleMenuDO>()
                .eq(RoleMenuDO::getRoleId, roleId)
                .in(RoleMenuDO::getMenuId, menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapper<RoleMenuDO>().eq(RoleMenuDO::getMenuId, menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<RoleMenuDO>().eq(RoleMenuDO::getRoleId, roleId));
    }

    default Set<Long> getMenuIdsByRoleIds(Set<Long> toList) {
        QueryWrapper<RoleMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(RoleMenuDO::getMenuId);
        wrapper.lambda().in(RoleMenuDO::getRoleId, toList);
        return selectList(wrapper).stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

}
