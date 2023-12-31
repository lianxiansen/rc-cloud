package com.rc.cloud.app.system.service.permission;


import com.rc.cloud.app.system.model.permission.SysMenuPO;
import com.rc.cloud.app.system.vo.permission.menu.MenuCreateReqVO;
import com.rc.cloud.app.system.vo.permission.menu.MenuListReqVO;
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
    String createMenu(MenuCreateReqVO reqVO);

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
    void deleteMenu(String id);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuPO> getMenuList();

    /**
     * 基于租户，筛选菜单列表
     * 注意，如果是系统租户，返回的还是全菜单
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SysMenuPO> getMenuListByTenant(MenuListReqVO reqVO);

    /**
     * 筛选菜单列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SysMenuPO> getMenuList(MenuListReqVO reqVO);

    /**
     * 获得所有菜单，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<SysMenuPO> getMenuListFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

    /**
     * 获得指定编号的菜单数组，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuIds       菜单编号数组
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单数组
     */
    List<SysMenuPO> getMenuListFromCache(Collection<String> menuIds, Collection<Integer> menuTypes,
                                         Collection<Integer> menusStatuses);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<SysMenuPO> getMenuListByPermissionFromCache(String permission);

    /**
     * 获得菜单
     *
     * @param id 菜单编号
     * @return 菜单
     */
    SysMenuPO getMenu(String id);

    /**
     * 通过用户编号，获得对应的菜单权限标识列表
     *
     * @param userId 用户编号
     * @return 权限标识列表
     */
    Set<String> getUserAuthorityByUserId(String userId);

    /**
     * 获取未禁用的用户的菜单列表
     *
     * @param userId   用户编号
     * @param parentId 父菜单编号
     * @param type     菜单类型
     * @return 菜单列表
     */
    List<SysMenuPO> getUsableUserMenuList(String userId, String parentId, Integer type);

    /**
     * 获取所有的用户的菜单列表，包括禁用的
     *
     * @param userId   用户编号
     * @param parentId 父菜单编号
     * @param type     菜单类型
     * @return 菜单列表
     */
    List<SysMenuPO> getUserMenuList(String userId, String parentId, Integer type);
}
