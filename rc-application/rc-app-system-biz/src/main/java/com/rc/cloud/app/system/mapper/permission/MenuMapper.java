package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MenuMapper extends BaseMapperX<SysMenuPO> {

    default SysMenuPO selectByParentIdAndName(String parentId, String name) {
        return selectOne(SysMenuPO::getParentId, parentId, SysMenuPO::getName, name);
    }

    default Long selectCountByParentId(String parentId) {
        return selectCount(SysMenuPO::getParentId, parentId);
    }

    default List<SysMenuPO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysMenuPO>()
                .likeIfPresent(SysMenuPO::getName, reqVO.getName())
                .eqIfPresent(SysMenuPO::getStatus, reqVO.getStatus()));
    }

    /**
     * 获得角色拥有的菜单编号集合
     * @param roleId 角色编号
     * @return 菜单编号集合
     * @author oliveoil
     * @date 2021/6/11 2:59 下午
     */
    default List<SysMenuPO> selectListByRoleId(String roleId) {

        return selectList(new LambdaQueryWrapperX<SysMenuPO>()
                .inSql(SysMenuPO::getId, "SELECT menu_id FROM sys_role_menu WHERE role_id = " + roleId));
    }

    /**
     * 根据菜单编号数组，获得菜单权限数组
     * @param menuIds
     * @return
     */
    default Set<String> getMenuPermissionListByMenuIds(Set<String> menuIds) {
        QueryWrapper<SysMenuPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysMenuPO::getPermission);
        wrapper.in("id", menuIds);
        return selectObjs(wrapper).stream()
                .map(String::valueOf)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toSet());
    }

    default List<SysMenuPO> selectPatentMenuList() {
        return selectList(new LambdaQueryWrapperX<SysMenuPO>()
                .eqIfPresent(SysMenuPO::getParentId, 0));
    }

    default Set<String> getMenuIdsByParentIdAndType(String parentId, Integer type) {
        return selectList(new LambdaQueryWrapperX<SysMenuPO>()
                .eqIfPresent(SysMenuPO::getParentId, parentId)
                .eqIfPresent(SysMenuPO::getType, type))
                .stream()
                .map(SysMenuPO::getId)
                .collect(Collectors.toSet());
    }
}
