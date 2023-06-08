package com.rc.cloud.app.system.biz.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.system.biz.model.permission.MenuDO;
import com.rc.cloud.app.system.biz.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default MenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(MenuDO::getParentId, parentId, MenuDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }

    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus()));
    }

    /**
     * 获得角色拥有的菜单编号集合
     * @param roleId 角色编号
     * @return 菜单编号集合
     * @author oliveoil
     * @date 2021/6/11 2:59 下午
     */
    default List<MenuDO> selectListByRoleId(Long roleId) {

        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .inSql(MenuDO::getId, "SELECT menu_id FROM role_menu WHERE role_id = " + roleId));
    }

    /**
     * 根据菜单编号数组，获得菜单权限数组
     * @param menuIds
     * @return
     */
    default Set<String> getMenuPermissionListByMenuIds(Set<Long> menuIds) {
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(MenuDO::getPermission);
        wrapper.in("id", menuIds);
        return selectObjs(wrapper).stream().map(String::valueOf).collect(Collectors.toSet());
    }
}
