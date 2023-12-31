package com.rc.cloud.app.system.service.permission;

import com.rc.cloud.app.system.model.permission.SysRolePO;
import com.rc.cloud.app.system.vo.permission.role.RoleCreateReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleExportReqVO;
import com.rc.cloud.app.system.vo.permission.role.RolePageReqVO;
import com.rc.cloud.app.system.vo.permission.role.RoleUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 角色服务接口
 */
public interface RoleService {

    /**
     * 初始化角色的本地缓存
     */
    void initLocalCache();

    /**
     * 创建角色
     *
     * @param reqVO 创建角色信息
     * @param type  角色类型
     * @return 角色编号
     */
    String createRole(@Valid RoleCreateReqVO reqVO, Integer type);

    /**
     * 更新角色
     *
     * @param reqVO 更新角色信息
     */
    void updateRole(@Valid RoleUpdateReqVO reqVO);

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void deleteRole(String id);

    /**
     * 更新角色状态
     *
     * @param id     角色编号
     * @param status 状态
     */
    void updateRoleStatus(String id, Integer status);

    /**
     * 设置角色的数据权限
     *
     * @param id               角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 部门编号数组
     */
    void updateRoleDataScope(String id, Integer dataScope, Set<String> dataScopeDeptIds);

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    SysRolePO getRoleFromCache(String id);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态。允许空，空时不筛选
     * @return 角色列表
     */
    List<SysRolePO> getRoleListByStatus(@Nullable Collection<Integer> statuses);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<SysRolePO> getRoleListFromCache(Collection<String> ids);

    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<SysRolePO> roleList);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnySuperAdmin(Set<String> ids) {
        return hasAnySuperAdmin(getRoleListFromCache(ids));
    }

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    SysRolePO getRole(String id);

    /**
     * 获得角色分页
     *
     * @param reqVO 角色分页查询
     * @return 角色分页结果
     */
    PageResult<SysRolePO> getRolePage(RolePageReqVO reqVO);

    /**
     * 获得角色列表
     *
     * @param reqVO 列表查询
     * @return 角色列表
     */
    List<SysRolePO> getRoleList(RoleExportReqVO reqVO);

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * 1. 角色编号不存在
     * 2. 角色被禁用
     *
     * @param ids 角色编号数组
     */
    void validateRoleList(Collection<String> ids);

    /**
     * 批量删除角色
     *
     * @param idList 角色编号数组
     */
    void deleteRoles(List<String> idList);
}
