package com.rc.cloud.app.system.service.permission;


import com.rc.cloud.app.system.api.permission.entity.SysMenuDO;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuSimpleRespVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuUpdateReqVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单 Service 接口
 *
 * @author 芋道源码
 */
public interface MenuService {

    /**
     * 初始化菜单的本地缓存
     */
    void initLocalCache();

    /**
     * 创建菜单
     *
     * @param reqVO 菜单信息
     * @return 创建出来的菜单编号
     */
    Long createMenu(MenuCreateReqVO reqVO);

    /**
     * 更新菜单
     *
     * @param reqVO 菜单信息
     */
    void updateMenu(MenuUpdateReqVO reqVO);

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     */
    void deleteMenu(Long id);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuDO> getMenuList();

    /**
     * 基于租户，筛选菜单列表
     * 注意，如果是系统租户，返回的还是全菜单
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SysMenuDO> getMenuListByTenant(MenuListReqVO reqVO);

    /**
     * 筛选菜单列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SysMenuDO> getMenuList(MenuListReqVO reqVO);

    /**
     * 获得所有菜单，从缓存中
     *
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes 菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<SysMenuDO> getMenuListFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

    /**
     * 获得指定编号的菜单数组，从缓存中
     *
     * 任一参数为空时，则返回为空
     *
     * @param menuIds 菜单编号数组
     * @param menuTypes 菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单数组
     */
    List<SysMenuDO> getMenuListFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes,
                                         Collection<Integer> menusStatuses);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<SysMenuDO> getMenuListByPermissionFromCache(String permission);

    /**
     * 获得菜单
     *
     * @param id 菜单编号
     * @return 菜单
     */
    SysMenuDO getMenu(Long id);

    /**
     * 获取根导航菜单列表
     * @return 菜单列表
     */
    List<SysMenuDO> getRootNavMenuList();

    Set<String> getUserAuthorityByUserId(Long userId);

    List<SysMenuDO> getUserChildMenuList(Long userId, Long parentId, Integer type);
}
