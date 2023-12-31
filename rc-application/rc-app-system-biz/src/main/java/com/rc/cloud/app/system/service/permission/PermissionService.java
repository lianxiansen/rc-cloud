package com.rc.cloud.app.system.service.permission;

import com.rc.cloud.app.system.api.permission.dto.DeptDataPermissionRespDTO;
import com.rc.cloud.app.system.model.permission.SysMenuPO;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 权限 Service 接口
 */
public interface PermissionService {

    /**
     * 初始化权限的本地缓存
     */
    void initLocalCache();

    /**
     * 获得角色们拥有的菜单列表，从缓存中获取
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param roleIds       角色编号数组
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<SysMenuPO> getRoleMenuListFromCache(Collection<String> roleIds, Collection<Integer> menuTypes,
                                             Collection<Integer> menusStatuses);

    /**
     * 获得用户拥有的角色编号集合，从缓存中获取
     *
     * @param userId       用户编号
     * @param roleStatuses 角色状态集合. 允许为空，为空时不过滤
     * @return 角色编号集合
     */
    Set<String> getUserRoleIdsFromCache(String userId, @Nullable Collection<Integer> roleStatuses);

    /**
     * 获得角色拥有的菜单编号集合
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    Set<String> getRoleMenuIds(String roleId);

    /**
     * 获得拥有多个角色的用户编号集合
     *
     * @param roleIds 角色编号集合
     * @return 用户编号集合
     */
    Set<String> getUserRoleIdListByRoleIds(Collection<String> roleIds);

    /**
     * 设置角色菜单
     *
     * @param roleId  角色编号
     * @param menuIds 菜单编号集合
     */
    void assignRoleMenu(String roleId, Set<String> menuIds);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<String> getUserRoleIdListByUserId(String userId);

    /**
     * 根据用户编号，获得菜单权限数组
     *
     * @param userId 用户编号
     * @return 菜单权限数组
     */
    Set<String> getPermissionListByUserId(String userId);

    /**
     * 设置用户角色
     *
     * @param userId  角色编号
     * @param roleIds 角色编号集合
     */
    void assignUserRole(String userId, Set<String> roleIds);

    /**
     * 设置角色的数据权限
     *
     * @param roleId           角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 部门编号数组
     */
    void assignRoleDataScope(String roleId, Integer dataScope, Set<String> dataScopeDeptIds);

    /**
     * 处理角色删除时，删除关联授权数据
     *
     * @param roleId 角色编号
     */
    void processRoleDeleted(String roleId);

    /**
     * 处理菜单删除时，删除关联授权数据
     *
     * @param menuId 菜单编号
     */
    void processMenuDeleted(String menuId);

    /**
     * 处理用户删除是，删除关联授权数据
     *
     * @param userId 用户编号
     */
    void processUserDeleted(String userId);

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(String userId, String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles  角色数组
     * @param userId 用户编号
     * @return 是否
     */
    boolean hasAnyRoles(String userId, String... roles);

    /**
     * 获得登陆用户的部门数据权限
     *
     * @param userId 用户编号
     * @return 部门数据权限
     */
    DeptDataPermissionRespDTO getDeptDataPermission(String userId);

}
