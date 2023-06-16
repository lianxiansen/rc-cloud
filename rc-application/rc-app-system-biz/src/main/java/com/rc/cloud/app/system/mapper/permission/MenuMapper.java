package com.rc.cloud.app.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.api.permission.model.SysMenuDO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MenuMapper extends BaseMapperX<SysMenuDO> {

    default SysMenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(SysMenuDO::getParentId, parentId, SysMenuDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(SysMenuDO::getParentId, parentId);
    }

    default List<SysMenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SysMenuDO>()
                .likeIfPresent(SysMenuDO::getName, reqVO.getName())
                .eqIfPresent(SysMenuDO::getStatus, reqVO.getStatus()));
    }

    /**
     * 获得角色拥有的菜单编号集合
     * @param roleId 角色编号
     * @return 菜单编号集合
     * @author oliveoil
     * @date 2021/6/11 2:59 下午
     */
    default List<SysMenuDO> selectListByRoleId(Long roleId) {

        return selectList(new LambdaQueryWrapperX<SysMenuDO>()
                .inSql(SysMenuDO::getId, "SELECT menu_id FROM role_menu WHERE role_id = " + roleId));
    }

    /**
     * 根据菜单编号数组，获得菜单权限数组
     * @param menuIds
     * @return
     */
    default Set<String> getMenuPermissionListByMenuIds(Set<Long> menuIds) {
        QueryWrapper<SysMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysMenuDO::getPermission);
        wrapper.in("id", menuIds);
        return selectObjs(wrapper).stream()
                .map(String::valueOf)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toSet());
    }
}
